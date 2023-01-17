package com.life.square.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.life.square.common.*;
import com.life.square.constants.MqConstants;
import com.life.square.pojo.*;
import com.life.square.service.*;
import com.life.square.utils.GetUserFromRedisUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 约吧模块
 *  包含：
 *      图片秀:
 *      视频秀
 *          点赞，收藏的接口和动态的一样
 */
@RestController
@RequestMapping("/recomment")
public class appointmentController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GetUserFromRedisUtil userFromRedisUtil;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private UserCollectionService userCollectionService;

    @Autowired
    private IVideoService videoService;

    @Autowired
    private RestClient restClient;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPictureService pictureService;

    @Autowired
    private IDiaryService diaryService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private LikeHobbyService likeHobbyService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JIEBA jieba;

    /**
     * 图片秀的筛选功能（年龄、距离、性别、标签）
     */
    private Map<String,Integer> currentPicSelectPage = new HashMap<String, Integer>();

    /**
     * 点赞动态
     */
    @PostMapping("/likeDiary")
    public R likeDiary(HttpServletRequest request,@RequestParam("diaryId") String diaryId){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";

            R r = new R();
            //判断是否已经点赞过
            UserLikeDiary userLikeDiary = diaryService.getOneLikeDiary(userId,diaryId);
            if(userLikeDiary != null){

                //删除点赞记录
                diaryService.deleteLikeDiaryRecord(userId,diaryId);
                //动态点赞量-1
                diaryService.subDiaryLikeAmount(diaryId);
                rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY, diaryId);
                r.setResultCode(200);
                r.setMsg("取消点赞成功");

                Diary diary = diaryService.getDiaryByDiaryId(diaryId);
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("total",diary.getDiaryLove());
                r.setMap(map);

                return r;
            }
            Integer id = null;
            //存储用户点赞的记录
            diaryService.addLikeDiaryRecord(id,userId,diaryId);

            diaryService.addDiaryLikeAmount(diaryId);
            rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY, diaryId);
            r.setResultCode(200);
            r.setMsg("点赞成功");
            Diary diary = diaryService.getDiaryByDiaryId(diaryId);
            Map<String,Integer> map = new HashMap<String, Integer>();

            if(diary!=null)
                map.put("total",diary.getDiaryLove());
            r.setMap(map);

            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 点赞 评论
     *      ps:删除点赞的记录，没有同步到索引库，且 记录索引库也没有创建
     */
    @PostMapping("/likeComment")
    public R cancelLikeDiary(HttpServletRequest request,@RequestParam("commentId") String commentId){

        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";

            System.out.println("----->");

            R r = new R();
            //判断是否已经点赞过
            UserLikeDiary userLikeDiary = diaryService.getOneLikeDiary(userId,commentId);
            if(userLikeDiary != null){

                //删除点赞记录
                diaryService.deleteLikeDiaryRecord(userId,commentId);
                //评论点赞量-1
                commentService.subCommentLikeAmount(commentId);
                rabbitTemplate.convertAndSend(MqConstants.COMMENT_EXCAHGNE,MqConstants.COMMENT_INSERT_KEY, commentId);
                Comment comment = commentService.getCommentById(commentId);
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("total",comment.getLikeAmount());
                r.setMap(map);
                r.setResultCode(200);    //0：取消点赞，点赞数量-1
                r.setMsg("取消点赞成功");
                System.out.println(r);
                return r;
            }
            Integer id = null;
            //存储用户点赞的记录
            diaryService.addLikeDiaryRecord(id,userId,commentId);
            //点赞量+1
            commentService.addCommentLikeAmount(commentId);
            rabbitTemplate.convertAndSend(MqConstants.COMMENT_EXCAHGNE,MqConstants.COMMENT_INSERT_KEY, commentId);
            r.setResultCode(200);
            r.setMsg("点赞成功");
            Comment comment = commentService.getCommentById(commentId);
            Map<String,Integer> map = new HashMap<String, Integer>();
            map.put("total",comment.getLikeAmount());
            r.setMap(map);
            System.out.println(r);
            return r;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 点赞动态的回复
     *      ps:删除点赞的记录，没有同步到索引库，且 记录索引库也没有创建
     */
    @PostMapping("/likeReply")
    public R likeReply(HttpServletRequest request,@RequestParam("commentId") String commentId){

        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";

            R r = new R();
            //判断是否已经点赞过
            UserLikeDiary userLikeDiary = diaryService.getOneLikeDiary(userId,commentId);
            if(userLikeDiary != null){

                //删除点赞记录
                diaryService.deleteLikeDiaryRecord(userId,commentId);
                //回复点赞量-1
                commentService.subReplyLikeAmount(commentId);
                rabbitTemplate.convertAndSend(MqConstants.REPLY_EXCAHGNE,MqConstants.REPLY_INSERT_KEY, commentId);
                Reply reply = commentService.getReplyById(commentId);
                Map<String,Integer> map = new HashMap<String, Integer>();
                map.put("total",reply.getLikeAmount());
                r.setMap(map);
                r.setResultCode(200);    //0：取消点赞，点赞数量-1
                r.setMsg("取消点赞成功");
                System.out.println(r);
                return r;
            }
            Integer id = null;
            //存储用户点赞的记录
            diaryService.addLikeDiaryRecord(id,userId,commentId);
            //点赞量+1
            commentService.addReplyLikeAmount(commentId);
            rabbitTemplate.convertAndSend(MqConstants.REPLY_EXCAHGNE,MqConstants.REPLY_INSERT_KEY, commentId);
            r.setResultCode(200);
            r.setMsg("点赞成功");
            Reply reply = commentService.getReplyById(commentId);
            Map<String,Integer> map = new HashMap<String, Integer>();

            map.put("total",reply.getLikeAmount());
            r.setMap(map);

            System.out.println(r);
            return r;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *收藏 or 取消收藏 图片秀或视频动态
     */
    @PostMapping("/collect")
    public JsonResult loveUser(HttpServletRequest request,@RequestParam("likedId") String likedId, @RequestParam("type")Integer type){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";

            JsonResult jsonResult = new JsonResult();
            Integer id = null;
            System.out.println(" 作者的id userId ： " +  userId+"  likedId : "+likedId);
            UserCollection userCollection = userCollectionService.getUserCollection(userId,likedId);
            if(userCollection!=null){
                userService.cancel_collect(userId,likedId);   //从数据库中删除收藏
                System.out.println("取消了"+userId+" 对 "+likedId+" 的收藏");
                //动态收藏量-1
                diaryService.subDiaryCollectAmount(likedId);
                rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY, likedId);
                if(userCollection!=null) {  //避免出现bug
                    String Id = userCollection.getId() + "";
                    rabbitTemplate.convertAndSend(MqConstants.COLLECT_EXCAHGNE, MqConstants.COLLECT_DELETE_KEY, Id);
                }
                jsonResult.setResultCode(200);
                jsonResult.setMessage("已取消收藏");
                return jsonResult;
            }
            userService.collect(id,userId,likedId,type);
            if(type == 1){  //1-视频动态
                //动态收藏量+1
                rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY, likedId);
                diaryService.addDiaryCollectAmount(likedId);
            }else if(type ==2){  //2-图片修收藏
                //图片修收藏量+1
            }
            String message = userId+" "+likedId;
            rabbitTemplate.convertAndSend(MqConstants.COLLECT_EXCAHGNE,MqConstants.COLLECT_INSERT_KEY, message);
            jsonResult.setResultCode(200);
            jsonResult.setMessage("收藏成功");
            return jsonResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 喜欢 or 取消喜欢 用户
     */
    @PostMapping("/likeUser")
    public JsonResult likeUser(HttpServletRequest request,@RequestParam("likedUserId") String likedUserId){
        try {
            JsonResult jsonResult = new JsonResult();  //存储结果
            JSONObject jsonObject = new JSONObject();
            Boolean likeEachOther = false;
//            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";   //用户的id
//            String userId = "3";

            //先判断用户是否是VIP，是的话就可以无限怎加喜欢人数
            //不是VIP的话，就最多只能喜欢15个
            //判断是否有15条喜欢的记录了,有了就不能继续喜欢
            Boolean isVip = isVip(userId);
            //获取喜欢用户记录  判断是否已经喜欢了这个人，如果是喜欢过的，再次访问这个接口表明是想取消喜欢
            LikeUser likeUser = userService.getLikeUser(userId,likedUserId);
            if(isVip == false){  //非vip
                //获取用户喜欢的人的所有id
                List<String> userList = userService.getLikeUserIds(userId);
                System.out.println(userId+"已经喜欢了"+userList.size()+"--->"+userList);
                if (likeUser != null) {
                    String m = "非vip不能取消喜欢,若想取消喜欢，请您充值vip";
                    jsonResult.setResultCode(504);
                    jsonResult.setMessage(m);
                    jsonResult.setData("");
                    return jsonResult;
                }
                if(userList.size()==15) {
                    String msg = "非vip喜欢的用户数量已达到了上限15个，若想继续添加喜欢，需要您开启充值vip";
                    jsonResult.setResultCode(200);
                    jsonResult.setMessage(msg);
                    jsonResult.setData(jsonObject);
                    return jsonResult;
                }
            }
            //vip可取消喜欢 + 无限添加喜欢
            if(likeUser != null){
                userService.cancelLikeUser(userId,likedUserId);
                System.out.println("取消了"+userId+"喜欢"+likedUserId);
                String s = "取消了"+userId+"喜欢"+likedUserId;
                jsonResult.setResultCode(200);
                jsonResult.setMessage(s);
                jsonResult.setData(jsonObject);
                return jsonResult;
            }
            //判断是否也被喜欢的人喜欢
//            LikeUser likeUser1 = userService.getLikeUser(userId, likedUserId);
            LikeUser likeUser1 = userService.getLikeUser(likedUserId, userId);
            if(likeUser1 != null){   //如果喜欢的人也喜欢自己
                likeEachOther = true;  //true互相喜欢
                //判断是否已经存在聊天了
                ChatFriend chatFriend = userService.findChatFriendRecord(userId,likedUserId);
                if(chatFriend == null) {
                    //创建好友关系
                    userService.insertChatFriend(userId, likedUserId);
                    userService.insertChatFriend(likedUserId, userId);
                }
            }
            userService.insertLikeUser(userId,likedUserId);
            String s = "添加了"+userId+"喜欢"+likedUserId;
            System.out.println("添加了"+userId+"喜欢"+likedUserId);
            jsonResult.setResultCode(200);
            jsonResult.setMessage(s);
            jsonObject.put("likeEachOther",likeEachOther);
            jsonResult.setData(jsonObject);
            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean isVip(String userId) {
        User user = userService.getUserByUser_id(userId);
        if(user!=null && user.getIsVip() == 2) {
            return true;
        }else if(user!=null && user.getIsVip() == 1)
            return false;
        return false;
    }

    /**
     * 不喜欢的该用户，添加到不喜欢的用户关系表种
     *
     * CREATE TABLE `tb_disable_date_dislike`  (
     *      `id` BIGINT(0)  '操作记录id',
     *       `user_id` BIGINT(0)  '用户id',
     *       `dislike_user_id`  '拉黑的人的id',
     *
     *
     * 思考：
     *  问题：
     *      用户已近点击了一次”不喜欢“，第二次点击的时候，是不能再往数据库里插入的数据的，以免数据重复。
     *  解决：
     *
     *
     *
     * @param request
     * @return
     */
    @PostMapping("/dislikeUser")
    public JsonResult dislikeUser(HttpServletRequest request){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//        String userId = "1";         //用户id

            String dislike_user_id = request.getParameter("dislikeUserId");   //拉黑的人的id
            Integer id = null;      //主键，自增长
            System.out.println(" 用户id user_id ： " + userId);
            System.out.println(" 拉黑的人的id dislike_user_id ： " + dislike_user_id);
            userService.dislikeUser(id,userId,dislike_user_id);
            String s = "添加了"+userId + "不喜欢" + dislike_user_id;
            return JsonResult.success(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取消不喜欢
     * @param request
     * @return
     */
    @DeleteMapping("/cancel-dislikeUser")
    public JsonResult cancel_dislikeUser(HttpServletRequest request){

        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String user_id = userFromRedis.getUserId()+"";
//        String user_id = "1";         //用户id

            String dislike_user_id = request.getParameter("likedUserId");   //拉黑的人的id
            System.out.println(" 用户id user_id ： " + user_id);
            System.out.println(" 拉黑的人的id dislike_user_id ： " + dislike_user_id);
            userService.cancel_dislikeUser(user_id,dislike_user_id);
            String s = "取消了" +user_id + "不喜欢"+dislike_user_id;
            return JsonResult.success(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//————————————————————————————————搜索功能 下————————————————————————————————————————————————————————————————————————————

    //缓存用户看的页数
    private Map<String,Integer> currentVideoPage = new HashMap<String, Integer>();
    /**
     * 视频秀搜索（ 根据固定的条件进行搜索 ）
     *    条件：
     *      第一层：距离10km内（没有则忽略）   【X】
     *      第二层：兴趣爱好的匹对（没有则忽略）
     *      第三层：无条件搜索（避免上两层条件没有结果数据了）
     */
    @GetMapping("/SearchVideoShowByAdvise")
    public JsonResult<JSONArray> SearchVideoShowByAdvise(HttpServletRequest request,@RequestParam("page") Integer page,@RequestParam("size")Integer size){
        try {
//            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String userId = userFromRedis.getUserId()+"";
            String userId = "1";

            currentVideoPage.put(userId,0);
            System.out.println("userId——————>"+userId);
            JSONArray jsonArray = new JSONArray();
//            BasicInfoDoc basicInfoDoc = new BasicInfoDoc(new BasicInfo());      //个人的基本信息
            BasicInfoDoc basicInfoDoc = infoService.getOneUserBasicInfoById(userId);  //获取个人的基本信息
            DetailInfo detailInfo = new DetailInfo();   //个人详细信息   --> 抽取出 兴趣爱好 + 我的标签
            Album album = new Album();   //个人相册

            //1.获取出距离10km内的所有用户id
            String index = "disable-date-detail-info";
            String filed = "location";
//           String location = basicInfoDoc.getLocation();
            String location = "";
            if(basicInfoDoc == null){
                //从数据库中查询个人基本信息
                BasicInfo basicInfo = infoService.getByPersonId(Integer.parseInt(userId));
                location = basicInfo.getLatitude() + ", " + basicInfo.getLongitude();  //纬度，经度
            }else
                location = basicInfoDoc.getLocation();
            System.out.println("location===>"+location);
            String distance = "10";
//            List<JSONObject> basicInDocList = infoService.getBasicInfoByDistance(index,filed,location,distance);

            //2.获取出兴趣爱好配对的所有用户的详细信息 --> 抽取出用户id
             List<DetailInfo> detailInfoList = getAllDetailInfoByHobbies(userId);

             //获取用户点赞过的视频id
            List<String> collectedDiaryIds = diaryService.getCollectedDiaryIds(userId);

            //3.合并前两个得到一个集合的id
            List<String> idList1 = new ArrayList<String>();
//            for(int i = 0;i<basicInDocList.size();i++){
//                BasicInfoDoc basicInfoDoc1 = (BasicInfoDoc) basicInDocList.get(0).get("BasicInfoDoc");
//                idList1.add(basicInfoDoc1.getPersonId()+"");
//            }
            List<String> idList2 = new ArrayList<String>();
            for(DetailInfo detailInfo1 : detailInfoList){
                idList2.add(detailInfo1.getPersonId()+"");
            }
            //获取 距离&爱好 条件的用户交集id
            List<String> idList = new ArrayList<String>();
            idList.addAll(idList2);
            System.out.println("交集id-->"+idList);

            //4.用集合的id作为选择性should搜索
            //用交集的用户id去从索引库搜索出所有的动态信息，并且进行时间的降序排序
            List<Diary> diaries = diaryService.getVideoDiariesByUserId(idList,page,size);
            Map<String,String> map = new HashMap<String, String>();
            map.put("diaryStatus","1");
            map.put("enableLook","1");
            map.put("diaryKind","2");
            List<Diary> diaryList = getDiary_matchNot_id(userId,diaries,page,size,idList,map);
            if(diaryList != null)
                diaries.addAll(diaryList);

            //5.根据动态的id 识别动态类型，并找出 路径
            jsonArray = findPicOrVideo(userId,jsonArray,diaries);
            JsonResult jsonResult = JsonResult.success(jsonArray);
            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //缓存用户看的页数
    private Map<String,Integer> currentPicShowPage = new HashMap<String, Integer>();
    /**
     * 图片秀搜索（ 根据固定的条件进行搜索图片 ）
     * 1.若进行筛选条件控制来查询，先根据筛选条件去搜索
     * 2.如果通过筛选条件筛选出的当前页面已经不足10条，就需要去用到推荐条件查（里面还设置了如果结合推荐条件查出来的当前页面也不够10条了，就无条件查询）
     *    1. 筛选条件 （年龄方位，距离范围，性别，个人标签）
     *    2. 推荐条件：
     *      第一层：距离10km内（没有则忽略）   【X】
     *      第二层：兴趣爱好的匹对（没有则忽略）
     *      第三层：无条件搜索（避免上两层条件没有结果数据了）
     */
    @GetMapping("/SearchPictureShowByAdvise")
    public JsonResult<JSONArray> SearchPictureShowByAdvise(HttpServletRequest request,
                                                           @RequestParam("age1")Integer age1,@RequestParam("age2") Integer age2,
                                                           @RequestParam("distance") Integer distance, @RequestParam("sex") Integer sex,
                                                           @RequestParam("tag") String tags,@RequestParam("page")Integer page,
                                                           @RequestParam("size") Integer size){
        try {
//            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String userId = userFromRedis.getUserId()+"";
            String userId = "1";

            Boolean isVip = isVip(userId);
            currentPicShowPage.put(userId,0);   //记录图片秀推荐的消息页数
            currentPicSelectPage.put(userId,0); //缓存筛选出来的消息页数
            System.out.println("userId——————>"+userId);
            JSONArray jsonArray = new JSONArray();
            //1.根据筛选条件去搜索
            if(sex!=null && !sex.equals("")) {
                selectPictureShow(userId,jsonArray, age1, age2, distance, sex, tags, page, size,isVip);
            }

            //2.如果通过筛选条件筛选出的当前页面已经不足10条，就需要去用到推荐条件查（里面还设置了如果结合推荐条件查出来的当前页面也不够10条了，就无条件查询）
            if(jsonArray.size() < size) {
                System.out.println("原始-->"+jsonArray.size());
                page = page - currentPicSelectPage.get(userId);
                Integer Size = size - jsonArray.size();
                getPicShowByadvice(userId,jsonArray,page,Size,size);
                System.out.println("后来-->"+jsonArray.size());
            }else
                currentPicSelectPage.put(userId,page);
            JsonResult jsonResult = new JsonResult();
//            if(isVip)
                jsonResult.setResultCode(200);
//            else
//                jsonResult.setResultCode(504);   //标识不是vip
            jsonResult.setData(jsonArray);
            return jsonResult;
//            return JsonResult.success(jsonArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getPicShowByadvice(String userId,JSONArray jsonArray, Integer page,Integer Size, Integer size) {

        try {

            Boolean isVip = isVip(userId);

            //            BasicInfoDoc basicInfoDoc = new BasicInfoDoc(new BasicInfo());      //个人的进本信息
//            BasicInfoDoc basicInfoDoc = infoService.getOneUserBasicInfoById(userId);  // todo 获取个人的基本信息
            System.out.println("————basicInfoDoc = infoService.getOneUserBasicInfoById(userId);  //获取个人的基本信息————");
            DetailInfo detailInfo = new DetailInfo();   //个人详细信息   --> 抽取出 兴趣爱好 + 我的标签
            Album album = new Album();   //个人相册

            //1.获取所有条件的id
            //1.1 获取出距离10km内的所有用户id
//            String index = "disable-date-basic-info";
//            String index = "tb_disable_date_person_basic_info";
            String index = "disable-date-detail-info";
            String filed = "location";
//            String location = basicInfoDoc.getLocation();
//            System.out.println("location===>"+location);
            String distance = "10";

            //1.2 获取出兴趣爱好配对的所有用户的详细信息 --> 抽取出用户id
            List<DetailInfo> detailInfoList = getAllDetailInfoByHobbies(userId);

            //1.3 获取用户喜欢的所有id
            List<String> likeUserIds = userService.getLikeUserIds(userId);

            //1.4 获取用户不喜欢的所有id
            List<String> disLikeUserIds = userService.getDisLikeUserIds(userId);

            //1.5 获取用户关注的所有id
            List<String> concernUserIds = userService.getConcernUserIds(userId);


            //2.整理前面所有id集合得到一个结果集合id
            List<String> idList1 = new ArrayList<String>();
//            for(int i = 0;i<basicInDocList.size();i++){
//                BasicInfoDoc basicInfoDoc1 = (BasicInfoDoc) basicInDocList.get(0).get("BasicInfoDoc");
//                idList1.add(basicInfoDoc1.getPersonId()+"");
//            }
            List<String> idList2 = new ArrayList<String>();
            for(DetailInfo detailInfo1 : detailInfoList){
                idList2.add(detailInfo1.getPersonId()+"");
            }
            //获取 距离&爱好 条件的用户交集id
            List<String> idList = new ArrayList<String>();
            idList.addAll(idList2);
            System.out.println("---->"+idList);
            idList.removeAll(likeUserIds);      //去除用户喜欢的id
            System.out.println("去除用户喜欢的id->"+likeUserIds+"后："+idList);
            idList.removeAll(disLikeUserIds);   //去除用户不喜欢的id
            System.out.println("去除用户不喜欢的id->"+disLikeUserIds+"后："+idList);
            idList.removeAll(concernUserIds);   //去除用户关注的id
            System.out.println("去除用户关注的id->"+concernUserIds+"后："+idList);
            idList.remove(userId);
            System.out.println("去除自己id->"+userId+"后："+idList);
            System.out.println("交集id-->"+idList);

            //3.用集合的id作为选择性should搜索

            Integer Index = (page - 1)*size;
            Integer sum = 0;

            jsonArray = getPicShowResult(jsonArray,idList,Index,Size,isVip,userId);
            System.out.println("===>"+jsonArray.size());
            if(jsonArray.size()<size){
                System.out.println("前——————————————>"+jsonArray.size());
                List<String> idNot = new ArrayList<String>();
                getUserNotIds(idList,idNot);
                System.out.println(idNot);
                idNot.removeAll(likeUserIds);      //去除用户喜欢的id
                System.out.println("去除用户喜欢的id->"+likeUserIds+"后："+idNot);
                idNot.removeAll(disLikeUserIds);   //去除用户不喜欢的id
                System.out.println("去除用户不喜欢的id->"+disLikeUserIds+"后："+idNot);
                idNot.removeAll(concernUserIds);   //
                System.out.println("去除用户关注的id->"+concernUserIds+"后："+idNot);
                idNot.remove(userId);
                System.out.println("去除自己id->"+userId+"后："+idNot);
                System.out.println("交集id-->"+idNot);
                page = page - currentPicShowPage.get(userId);
                Size = size - jsonArray.size();
                Index = (page - 1)*size;
                jsonArray = getPicShowResult(jsonArray,idNot,Index,Size,isVip,userId);
                System.out.println("后——————————————>"+jsonArray.size());
            }else {
                currentPicShowPage.put(userId,page);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 图片秀的搜索框搜索功能(通过输入关键字搜索)
     *  输入名字搜索人
     */
    @GetMapping("/searchPictureShowByKeywords")
//    public JsonResult<JSONArray> searchPictureShowByKeywords(HttpServletRequest request,
    public R searchPictureShowByKeywords(HttpServletRequest request,
                                                 @RequestParam("keyword") String keyword,
                                                 @RequestParam("page")Integer page,
                                                 @RequestParam("size") Integer size){
        try {
            JSONArray jsonArray = new JSONArray();

            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//            String userId  = "11";

            Boolean isVip = isVip(userId);
            List<String> words = jieba.JIEBA(keyword);
            System.out.println(words);
            Set<String> keywords = new HashSet<String>();
            for(String word : words){
                keywords.add(word);
            }
            System.out.println("1111111111111111");
            Integer maxUsers = userService.list().size();
            System.out.println("到这了吗");
            //根据输入的用户名进行模糊查询
            List<BasicInfo> basicInfos = userService.getUsersByMatchUserName(keywords,maxUsers);
            System.out.println("根据用户名模糊查询的用户列表：");
            System.out.println(basicInfos);
            List<String> idList = new ArrayList<String>();
            for(BasicInfo basicInfo : basicInfos){
                idList.add(basicInfo.getPersonId()+"");
            }

            //关键字子搜索 得到 匹对的用户详细信息的（用详细信息的【标签，爱好】来进行匹对）
            List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
            System.out.println(keywords);
            Integer max = infoService.getAmount();
            detailInfoList = userService.getDetailByMatchKeyWords(keywords,max);
            for ( DetailInfo detailInfo : detailInfoList ) {
                idList.add(detailInfo.getPersonId()+"");
            }
            Set<String> set = new HashSet<>();      //去重
            for(int i = 0;i<idList.size();i++){
                set.add(idList.get(i));
            }
            System.out.println("总数--->"+set.size());
            System.out.println("——————————————————————————————————————————————————————————————————————————————————");
            System.out.println(idList);
            System.out.println("--idList--->"+idList.size());
            System.out.println("——————————————————————————————————————————————————————————————————————————————————————");
            Integer index = (page - 1)*size;
            jsonArray = getPicShowResult(jsonArray,idList,index,size,isVip,userId);

//            JsonResult jsonResult = new JsonResult();
//            jsonResult.setResultCode(200);
//            jsonResult.setData(jsonArray);
            R r = new R();
            r.setResultCode(200);
            r.setData(jsonArray);
            Map map = new HashMap();
            map.put("total",set.size());
            r.setMap(map);
            r.setMsg("map中的total代表的是能搜索出的总数量");
            return r;
//            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 视频秀的搜索框搜索功能(通过输入关键字搜索)  匹对的字段 ： 动态标题 、 动态内容
     *      搜获
     */
    @GetMapping("/searchDiaryShowByKeywords")
//    public JsonResult<JSONArray> searchDiaryShowByKeywords(HttpServletRequest request,
    public R searchDiaryShowByKeywords(HttpServletRequest request,
                                                @RequestParam("keyword") String keyword,@RequestParam("page")Integer page,
                                                 @RequestParam("size") Integer size){
        try {
            JSONArray jsonArray = new JSONArray();

            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//        String userId = "11";

            //关键字子搜索 得到 视频秀的搜索框搜索功能(通过输入关键字搜索)  匹对的字段 ： 动态标题 、 动态内容
            List<Diary> diaries = new ArrayList<Diary>();

            //对输入的关键字进行分词
            List<String> words = jieba.JIEBA(keyword);
            System.out.println(words);
            Set<String> keywords = new HashSet<String>();
            for(String word : words){
                keywords.add(word);
            }
            System.out.println(keywords);
            //视频秀的搜索框搜索功能(通过输入关键字搜索)  匹对的字段 ： 动态标题 、 动态内容
            diaries = diaryService.getVideoDiaryByMatchKeyWords(keywords,page,size);

            jsonArray = findPicOrVideo(userId,jsonArray,diaries);

            Integer total = 0;
            for(int i = page;;i++){
                List<Diary> diaryList = new ArrayList<>();
                diaryList = diaryService.getVideoDiaryByMatchKeyWords(keywords,i,size);
                if(diaryList != null && diaryList.size() != 0)
                    total += diaryList.size();
                break;
            }
            R r = new R();
            r.setResultCode(200);
            r.setData(jsonArray);
            Map map = new HashMap();
            map.put("total",total);
            r.setMap(map);
            r.setMsg("map中的total代表的是能搜索出的总数量");

//            JsonResult jsonResult = new JsonResult();
//            jsonResult.setData(jsonArray);

            return r;
//            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public JSONArray selectPictureShow(String userId,JSONArray jsonArray,Integer age1,Integer age2,Integer distance, Integer sex, String tags,Integer page,Integer size,Boolean isVip){
//        String userId = "1";
        currentVideoPage.put(userId,0);
//        JSONArray jsonArray = new JSONArray();
        System.out.println(age1+"->"+age2+" "+distance+" "+sex);
        //1.根据 年龄范围、距离范围、性别 筛选出所有的用户基本信息
        List<BasicInfoDoc> basicInfoDocList = new ArrayList<BasicInfoDoc>();
        basicInfoDocList = infoService.getBasicInfosByAgeDistanceSex(age1,age2,distance,sex);

        //2.根据 标签 筛选出所有的用户详细信息
        List<String> words = jieba.JIEBA(tags);
        Set<String> set = new HashSet<String>();
        for(String w : words){
            set.add(w);
        }
        System.out.println(words);
        List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
        String esName  = "personTag";
//        String index = "tb_disable_date_person_detail_info";
        String index = "disable-date-detail-info";
        detailInfoList = infoService.getAllUserDetailFromIndexMatchEsname(index,esName,set);

        //3.前面两个的用户id集合，取交集id
        List<String> idList1 = new ArrayList<String>();
        for(BasicInfoDoc basicInfoDoc : basicInfoDocList){
            idList1.add(basicInfoDoc.getPersonId()+"");
        }
        List<String> idList2 = new ArrayList<String>();
        for(DetailInfo detailInfo : detailInfoList){
            idList2.add(detailInfo.getPersonId()+"");
        }
        List<String> idList = new ArrayList<String>();
        idList.addAll(idList1);
        idList.retainAll(idList2);
        System.out.println("交集userId————>"+idList);

        //1.3 获取用户喜欢的所有id
        List<String> likeUserIds = userService.getLikeUserIds(userId);
        //1.4 获取用户不喜欢的所有id
        List<String> disLikeUserIds = userService.getDisLikeUserIds(userId);
        //1.5 获取用户关注的所有id
        List<String> concernUserIds = userService.getConcernUserIds(userId);
        idList.removeAll(likeUserIds);      //去除用户喜欢的id
        System.out.println("去除用户喜欢的id->"+likeUserIds+"后："+idList);
        idList.removeAll(disLikeUserIds);   //去除用户不喜欢的id
        System.out.println("去除用户不喜欢的id->"+disLikeUserIds+"后："+idList);
        idList.removeAll(concernUserIds);   //去除用户关注的id
        System.out.println("去除用户关注的id->"+concernUserIds+"后："+idList);
        idList.remove(userId);
        System.out.println("去除自己id->"+userId+"后："+idList);
        System.out.println("最后的筛选id交集————>"+idList);

        //4.用交集id去搜索出图片秀
        Integer start = (page - 1)*size;
        getPicShowResult(jsonArray,idList,start,size,isVip,userId);

        return jsonArray;
    }

    //获取idList除外的所以有其它用户id
    public void getUserNotIds(List<String> idList, List<String> notIds) {
        List<User> userList = userService.list();
        for(User user : userList){
            String user_id = user.getUserId()+"";
            if(!idList.contains(user_id))   //如果不包含
                notIds.add(user.getUserId()+"");
        }
    }

    /**
     * 视频秀的筛选功能（年龄、距离、性别、标签）
     */
    @GetMapping("/selectVideoShow")
    public JsonResult<JSONArray> selectVideoShow(HttpServletRequest request,
                                    @RequestParam("age1")Integer age1,@RequestParam("age2") Integer age2,
                                     @RequestParam("distance") Integer distance, @RequestParam("sex") Integer sex,
                                     @RequestParam("tag") String tags,@RequestParam("page")Integer page,
                                     @RequestParam("size") Integer size){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//            String userId = "1";
            currentVideoPage.put(userId,0);
            JSONArray jsonArray = new JSONArray();
            System.out.println(age1+"->"+age2+" "+distance+" "+sex);
            //1.根据 年龄范围、距离范围、性别 筛选出所有的用户基本信息
            List<BasicInfoDoc> basicInfoDocList = new ArrayList<BasicInfoDoc>();
            basicInfoDocList = infoService.getBasicInfosByAgeDistanceSex(age1,age2,distance,sex);

            //2.根据 标签 筛选出所有的用户详细信息
            List<String> words = jieba.JIEBA(tags);
            Set<String> set = new HashSet<String>();
            for(String w : words){
                set.add(w);
            }
            System.out.println(words);
            List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
            String esName  = "personTag";

//            String index = "tb_disable_date_person_detail_info";
            String index = "disable-date-detail-info";
            detailInfoList = infoService.getAllUserDetailFromIndexMatchEsname(index,esName,set);

            //3.前面两个的用户id，取交集id
            List<String> idList1 = new ArrayList<String>();
            for(BasicInfoDoc basicInfoDoc : basicInfoDocList){
                idList1.add(basicInfoDoc.getPersonId()+"");
            }
            List<String> idList2 = new ArrayList<String>();
            for(DetailInfo detailInfo : detailInfoList){
                idList2.add(detailInfo.getPersonId()+"");
            }
            List<String> idList = new ArrayList<String>();
            idList.addAll(idList1);
            idList.retainAll(idList2);
            System.out.println("交集userId————>"+idList);

            //4.用交集id去搜索出id视频动态
            //用交集的用户id去从索引库搜索出所有的动态信息，并且进行时间的降序排序
            List<Diary> diaries = diaryService.getVideoDiariesByUserId(idList,page,size);
            //获取非交集id部分的其它所有动态信息(如果筛选条件查询出的视频已经没有了，就用非条件查询)
            Map<String,String> map = new HashMap<String, String>();
            map.put("diaryStatus","1");  //发布成功状态
            map.put("enableLook","1");   //可见
            map.put("diaryKind","2");   //视频类
            map.put("isDeleted","0");  //0-未被删除
            map.put("isReport","0");     //0-举报失败或未被举报 1-举报成功
            List<Diary> diaryList = getDiary_matchNot_id(userId,diaries,page,size,idList,map);
            if(diaryList != null)
                diaries.addAll(diaryList);

            //5.根据动态的id 识别动态类型，并找出 路径
            jsonArray = findPicOrVideo(userId,jsonArray,diaries);


            return JsonResult.success(jsonArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //合并和整合 得到一个 JSONArray类型的结果
    public JSONArray getPicShowResult(JSONArray jsonArray,List<String> idList, Integer index, Integer size,Boolean isVip,
                                      String userId) {
        System.out.println(idList.size()+"个用户");
        System.out.println("index-->"+index+"   size——>"+size);
        for(int i = index;i<index+size;i++){   //Index --> index + size
            if(idList.size()>i) {
                String user_id = idList.get(i);
                System.out.println("找--->"+user_id);
                JSONObject jsonObject = new JSONObject();
                getPictureShowInfo(jsonObject,user_id,isVip,userId);  //获取一个用户的图片秀的数据
                jsonArray.add(jsonObject);
//                jsonObject.put("total",idList.size());
            }
        }
        return jsonArray;
    }

    //获取用户视频秀的所有数据
    public void getPictureShowInfo(JSONObject jsonObject,String user_id,Boolean isVip,String userId) {


        //用交集的用户id去从数据库中找出用户个人相册
        List<Album> albumList = userService.getAlbumByUserId(user_id);
//        //从数据库中查询MV
//        MV mv = userService.getMVById(user_id);
        //从数据库中获取出用户的详细信息
        DetailInfo detailInfo = userService.getDetailInfoByUserId(user_id);
        //从数据库中获取出用户的基本信息
        BasicInfo basicInfo = userService.getBasicInfoByUserId(user_id);
        //从数据库中出user信息
        User user = userService.getUserByUser_id(user_id);
        //查询关注记录
        Follow follow = userService.getFollowRecord(userId,user_id);

        Integer alreadyCollect = 0;  //是否已经收藏
        int alreadyConcern = 0;   //是否已经关注
        String disableType = "";  //残疾种类
        String degree = "";  //最高学历
        Integer age = 0;  //年龄
        Integer hight = 0; //身高
        String userName = "";     //用户名
        String mvPath  = "";      //视频路径
//        String mvCoverPath = "";  //封面图片路径
        String headPath = "";
        String[] tags = null;
        String[] hobbies = null;
        Integer disableLevel = 0;   //残疾等级
        String address = "";
        String loca = "";
        //根据用id判断用户是否对这条动态 收藏了（1-收藏了 0-为收藏）
        UserCollection userCollection = userCollectionService.getUserCollection(userId,user_id);
        System.out.println("—————————————————————————————————收藏记录———————————————————————————————————————————————————");
        System.out.println(userId+"------"+user_id);
        System.out.println(userCollection);
        System.out.println("—————————————————————————————————收藏记录————————————————————————————————————————————————————");
        if(userCollection!=null) {
            alreadyCollect = 1;
        }
        System.out.println("-------谁啊------->" + user_id);
        List<String> picList = new ArrayList<String>();
        //是会员才可以看 个人相册 和 mv
        if(isVip) {
            if (albumList != null && albumList != null)
                for (Album album1 : albumList) {
                    picList.add(album1.getPicPath());
                }
            if(basicInfo != null){
                mvPath = basicInfo.getMv();
            }

//            if(mv!=null) {
//                mvPath = mv.getVideoPath();
//                mvCoverPath = mv.getCoverPath();
//            }
        }
        if(user!=null && user.getHeadPicPath() != null) {
            headPath = user.getHeadPicPath();
            userName = user.getNickName();
        }
        if (detailInfo != null && detailInfo.getPersonTag() != null) {
            tags = detailInfo.getPersonTag().split(" ");
        }
        if (detailInfo != null && detailInfo.getHobby() != null) {
            hobbies = detailInfo.getHobby().split(" ");
            disableLevel = detailInfo.getDisableLevel();    //残疾等级 1-健康 2-轻度残疾 3-中度残疾 4-重度残疾 5-极重度残疾
            disableType = detailInfo.getDisableType();
        }
        if (basicInfo != null && basicInfo.getHouseholdAddr() != null) {
            address = basicInfo.getHouseholdAddr();
            age = basicInfo.getAge();
            hight = basicInfo.getHeight();
            degree = basicInfo.getDegree();
        }
        if (basicInfo != null && basicInfo.getLatitude() != null && basicInfo.getLongitude() != null) {
            loca = basicInfo.getLatitude() + ", " + basicInfo.getLongitude();
        }
        if(userId != null){
            alreadyConcern = 1;
        }
        jsonObject.put("userId",user_id);                               //用户id
        jsonObject.put("username",userName);                            //用户名
        jsonObject.put("houseAddress", address);                        //家庭住址
        jsonObject.put("disableLevel",disableLevel);  //残疾等级 1-健康 2-轻度残疾 3-中度残疾 4-重度残疾 5-极重度残疾
        jsonObject.put("headPath",headPath);                            //头像
        jsonObject.put("picShow", picList);                              //个人相册
        jsonObject.put("mvPath",mvPath);                                //mv路径
//        jsonObject.put("mvCoverPath",mvCoverPath);                      //mv封面路径
        jsonObject.put("tags", tags);                                    //我的标签
        jsonObject.put("hobbies", hobbies);                             //我的兴趣
        jsonObject.put("location", loca);                                //经纬度
        jsonObject.put("age",age);                                      //年龄
        jsonObject.put("hight",hight);                                  //身高
        jsonObject.put("degree",degree);                                //最高学历
        jsonObject.put("disableType",disableType);                      //残疾种类
        jsonObject.put("alreadyConcern",alreadyConcern);                //是否已经关注  0-为关注 1-已关注
        jsonObject.put("alreadyCollect",alreadyCollect);                //收藏         0-未收藏 1-收藏了
        jsonObject.put("isVip",isVip);                                  //标志vip true:是vip false:非vip
    }


    //获取非交集id部分的其它所有动态信息
    public List<Diary> getDiary_matchNot_id(String userId,List<Diary> diaries, Integer page, Integer size,List<String> idList,
                                        Map<String,String> map) {
        List<Diary> diaryList = new ArrayList<Diary>();
        if(diaries.size() < size ){
            page = page - currentVideoPage.get(userId);
            size = size - diaries.size();
            //获取非交集id的用户动态
            diaryList = diaryService.getDiariesByNotUserId(idList,map,page,size);
//            diaries.addAll(diaryList);
        }else{
//            currentVideoPage = page;   //缓存好第几页
            currentVideoPage.put(userId,page);
        }
        return diaryList;
    }

    //根据动态的id 识别动态类型，并找出 路径 --> 合并和整合 得到一个 JSONArray类型的结果
    public JSONArray findPicOrVideo(String userId,JSONArray jsonArray, List<Diary> diaries) {
        try {

//            String . = "1";

            for(Diary diary : diaries){
                JSONObject jsonObject = new JSONObject();

                BasicInfo basicInfo = userService.getBasicInfoByUserId(diary.getDiaryUserId());
                String location = "";
                if(basicInfo!=null)
                    location = basicInfo.getLatitude() + ", " + basicInfo.getLongitude();  //纬度，经度
                jsonObject.put("location",location);

                //根据用户id判断用户是否对这条动态点赞了（1-点赞了 0-未点赞）
                UserLikeDiary userLikeDiary = diaryService.getOneLikeDiary(userId,diary.getDiaryId());
                if(userLikeDiary!=null) {
                    jsonObject.put("alreadyLike", 1);  //点赞了
                }
                else {
                    jsonObject.put("alreadyLike", 0);  //未点赞
                }

                //根据用id判断用户是否对这条动态 收藏了（1-收藏了 0-为收藏）
                UserCollection userCollection = userCollectionService.getUserCollection(userId,diary.getDiaryId());
                if(userCollection!=null)
                    jsonObject.put("alreadyCollect",1);  //收藏了
                else
                    jsonObject.put("alreadyCollect",0);  //未收藏

                //查询关注记录
                Follow follow = userService.getFollowRecord(userId,diary.getDiaryUserId());
                int alreadyConcern = 0;   //是否已经关注
                if(follow != null)
                    alreadyConcern = 1;
                jsonObject.put("alreadyConcern",alreadyConcern);

                jsonObject.put("flag",1);

                jsonObject.put("diary",diary);                  //添加动态信息

                User user = userService.getUserByUser_id(diary.getDiaryUserId());
                String head_path = user.getHeadPicPath();
                jsonObject.put("headPicture",head_path);        //头像

                if(diary.getDiaryKind() == 1){   //1-代表图文动态
                    List<String> pictures = new ArrayList<String>();
                    pictures = pictureService.getPathById("diaryId",diary.getDiaryId());
                    List<String> picPath = new ArrayList<String>();
                    jsonObject.put("picture",pictures);          //添加 配图的路径
                }else if(diary.getDiaryKind() == 2){  //2-视屏类动态
                    List<String> videoPaths = videoService.getPathById("diaryId",diary.getDiaryId());
                    if(videoPaths.size()>0) {
                        jsonObject.put("video",videoPaths.get(0));          //添加 视频的路径
                    }
                }
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //获取出 兴趣爱好配对 的所有用户的详细信息
    public List<DetailInfo>  getAllDetailInfoByHobbies(String userId){
        List<LikeHobby> likeHobbies = likeHobbyService.getUserLikeHobbisByUserId(userId);
        System.out.println("爱好队列-->"+likeHobbies);
        Set<String> hobbiesSet = new HashSet<String>();
        for(LikeHobby likeHobby : likeHobbies){
            List<String> jiebaWords = jieba.JIEBA(likeHobby.getHobby());
            for(String HB : jiebaWords){
                hobbiesSet.add(HB);
            }
        }
        for(String w : hobbiesSet ){
            System.out.print(w+" ");
        }
        List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
        detailInfoList = infoService.getAllUserDetailFromIndexMatchHobby(hobbiesSet);
        return detailInfoList;
    }
}
