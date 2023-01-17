package com.life.square.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.life.square.VO.VoPaths;
import com.life.square.common.JsonResult;
import com.life.square.common.Save;
import com.life.square.pojo.*;
import com.life.square.service.*;
import com.life.square.common.RestClient;
import com.life.square.constants.MqConstants;
import com.life.square.utils.GetUserFromRedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 有关动态的接口
 */
@RestController
@RequestMapping("/social")
public class diaryController {

    @Autowired
    private GetUserFromRedisUtil userFromRedisUtil;

    @Autowired
    private appointmentController appointmentContro;

    @Autowired
    private IDiaryService diaryService;

    @Autowired
    private IPictureService pictureService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private RestClient restClient;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVideoService videoService;

    //输入发送消息的API
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //保存文件到服务器
    @Autowired
    private Save save = new Save();

    /**
     * 发表个人动态
     * @param
     * @return
     */
    @PostMapping("/active/publishActive")
    public JsonResult put_Diary( HttpServletRequest request,
            @RequestParam("diaryTitle") String diary_title,             //朋友圈标题
            @RequestParam("diaryContent") String diary_content,        //朋友圈内容
            @RequestParam("diaryStatus") Integer diary_status,        //0-审核不通过 1-审核通过 2-待审核 3-草稿
            @RequestParam("enableLook") String enableLook,           //0-可见 2-不可见
            @RequestParam("enableComment") String enable_comment,   //1-允许评论 2-不允许评论
            @RequestBody VoPaths voPaths
    ) {
        System.out.println(diary_title+" "+diary_content+" "+diary_status+" "+enableLook+" "+enable_comment);
        try {
            JsonResult jsonResult = new JsonResult();
            List<String> picPaths = voPaths.getPicPaths();
            List<String> videoPaths = voPaths.getVideoPaths();

            //通过令牌来获取
            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String diary_user_id = userFromRedis.getUserId()+"";
//            String diary_user_name = userFromRedis.getLoginName();

            String diary_user_id = "1";
            String diary_user_name = "哈哈哈哈";

            System.out.println();
            Integer diaryKind = 1;
            Integer id = null;  //主键id，自增长
            String diary_id = UUID.randomUUID()+"";   //动态唯一id
//            if(picPaths!=null)
            if(picPaths.size()>0) {
                System.out.println("——————— if(picPaths!=null) {———————"+picPaths.size());
                diaryKind = 1;
                if(picPaths.size() > 6){
                    jsonResult.setResultCode(500);
                    jsonResult.setMessage("图片超过了6张，普通用户请控制少于6张，或者开启VIP");
                    return jsonResult;
                }else {
                    for(String path : picPaths){
                        System.out.println("------>"+path);
                        String name = UUID.randomUUID()+".jpg";     //使用UUID命名，避免重复问题
                        pictureService.insertPicture(id,diary_id,name,diary_user_id,path,2);
                        rabbitTemplate.convertAndSend(MqConstants.PICTURE_EXCAHGNE,MqConstants.PICTURE_INSERT_KEY, diary_id);
                    }
                }
            }

//            if(videoPaths!=null)
            if(videoPaths.size()>0){
                System.out.println("——————— else if(videoPaths!=null){———————"+videoPaths.size());
                diaryKind = 2;
                if(videoPaths.size()>2) {
                    return JsonResult.success("视频只能上传一个");
                } else {
                    for(String path : videoPaths){
                        System.out.println("----videoPaths-->"+path);
                        String name = UUID.randomUUID()+".jpg";     //使用UUID命名，避免重复问题
                        videoService.insertVideo(id,name,diary_id,diary_user_id,path);
                        rabbitTemplate.convertAndSend(MqConstants.DIARYVIDEO_EXCAHGNE,MqConstants.DIARYVIDEO_INSERT_KEY, name);
                    }
                }
            }

//            for(String path : picPaths){
//                String name = UUID.randomUUID()+".jpg";     //使用UUID命名，避免重复问题
//                if(diaryKind == 1){   //1-图文动态
//                    pictureService.insertPicture(id,diary_id,name,"2",path,2);
//                    rabbitTemplate.convertAndSend(MqConstants.PICTURE_EXCAHGNE,MqConstants.PICTURE_INSERT_KEY, diary_id);
//                }else if(diaryKind == 2){   //2-视频动态
//                    videoService.insertVideo(id,diary_id,diary_user_id,name);
//                    rabbitTemplate.convertAndSend(MqConstants.DIARYVIDEO_EXCAHGNE,MqConstants.DIARYVIDEO_INSERT_KEY, name);
//                }else if(diaryKind == 3){ //3-语音类动态
//
//                }
//            }

            diaryService.insertDiary(id,diary_id,diary_user_id,diary_user_name,diary_title,diary_content,diaryKind,diary_status,enable_comment);
            //同步动态的索引库的文档
//     数据同步请求发送                                    交换机                       RoutingLKey     内容
            rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY, diary_id);
            //同步照片的索引库的文档
            rabbitTemplate.convertAndSend(MqConstants.PICTURE_EXCAHGNE,MqConstants.PICTURE_INSERT_KEY, diary_id);

            jsonResult.setResultCode(200);
            jsonResult.setData(null);
            jsonResult.setMessage("待审核状态,后转到后台管理系统审核通过后才可以发布公开");
            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 发表评论
     */
    @PostMapping("/active/publishComment")
    public JsonResult publishComment(HttpServletRequest request){
        try {
            String comment = request.getParameter("comment");                                       //评论的内容
            String  diary_id = request.getParameter("diaryId");                                      //动态的id
            //通过令牌来获取
            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String commentator_ip = userFromRedis.getUserId()+"";             //评论者的id
//            String commentator_name = userFromRedis.getLoginName();          //评论者昵称
        String commentator_ip = "1";                         //评论者的id
        String commentator_name = "hhhhhhhhh";                      //评论者昵称


            String comment_id = UUID.randomUUID()+"";                //评论id
            Integer is_deleted = 1;                                                                    //未删除
            Integer id = null;   //主键id，自增长

            boolean flag = true;

            //添加评论到评论表
            diaryService.insertComment(id,comment_id,comment,diary_id,commentator_ip,commentator_name,is_deleted);
            System.out.println("comment_id = "+comment_id );
            rabbitTemplate.convertAndSend(MqConstants.COMMENT_EXCAHGNE,MqConstants.COMMENT_INSERT_KEY,comment_id);

            //给对应的动态的评论量+1
            diaryService.commentAddOne(diary_id);
            rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY,diary_id);

            return JsonResult.success();  //表示审核通过 并 存入成功
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 回复评论
     *  *   `comment_id` VARCHAR(60)            '评论的id',
     *  *   `reply_content` VARCHAR(11)         '回复内容',
     *  *   `reply_user_id` VARCHAR(11)         '回复者id',
     *  *   `reply_user_name` VARCHAR(11)       '回复者的名称',
     *  *   `comment_user_id` VARCHAR(11)       '被恢复的评论的用户id',
     *  *   `comment_user_name` VARCHAR(11)     '被恢复的评论的用户名称',
     *  *   `reply_status` TINYINT(0)           '是否审核通过 0-审核不通过 1-审核通过 2-未审核' ,
     *  *   `create_time` DATETIME(0)           '创建时间',
     * @param request
     * @return
     */
    @PostMapping("/active/publishCommentReply")
    public JsonResult publishCommentReply(HttpServletRequest request){

        String comment_id = request.getParameter("comment_id");                      //评论id
        String diary_id = request.getParameter("diary_id");                         //动态id
        String reply_content = request.getParameter("reply_content");               //回复内容
        String comment_user_id = request.getParameter("comment_user_id");           //被回复的评论的用户id
        String comment_user_name = request.getParameter("comment_user_name");       //被回复的评论的用户名称
        String  reply_id = UUID.randomUUID()+"";                                        //回复的id

        try {
            //通过令牌来获取
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String reply_user_id = userFromRedis.getUserId()+"";             //回复者id
            String reply_user_name = userFromRedis.getLoginName();           //回复者的名称
//        String reply_user_id = "1";               //回复者id
//        String reply_user_name = "路费";           //回复者的名称

            Integer id = null;                                                              //自增长的主键id

            System.out.println("回复的内容 reply_content : "+reply_content);
            System.out.println("回复者id reply_user_id : "+reply_user_id);
            System.out.println("回复者的名称 reply_user_name : " +reply_user_name );
            System.out.println("被恢复的评论的用户id comment_user_id : "+comment_user_id);
            System.out.println("被恢复的评论的用户名称 comment_user_name : "+comment_user_name);

            //添加评论的回复到回复表
            diaryService.insertReply(id,reply_id,comment_id,diary_id,reply_content,reply_user_id,reply_user_name,comment_user_id,comment_user_name);
            rabbitTemplate.convertAndSend(MqConstants.REPLY_EXCAHGNE,MqConstants.REPLY_INSERT_KEY,reply_id);

            //给对应的动态的评论量 +1
            diaryService.commentAddOne(diary_id);
            rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY,diary_id);

            //给对应的动态的回复量 +1
            diaryService.commentReplyAmountAddOne(comment_id);
            rabbitTemplate.convertAndSend(MqConstants.COMMENT_EXCAHGNE,MqConstants.COMMENT_INSERT_KEY,comment_id);


            return JsonResult.success();  //存入成功
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 统计附近的动态的总数连
     */
    @GetMapping("/getNearTotal")
    public JsonResult<Integer> getNearPages(HttpServletRequest request){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";



            Integer total = 0;
            for(int page = 1;;page++){
                String name = "createTime";  //根据这个索引库的字段来进行排序
                String sortOrder = "DESC";
                //获取多条动态
                List<Diary> diaries = diaryService.gertNearActiveList(name,page, 10, sortOrder);
                if(diaries == null || diaries.size() == 0){
                    break;
                }else{
                    total += diaries.size();
                }
            }
            return JsonResult.success(total);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 统计关注的动态的总数连
     */
    @GetMapping("/getFollowPages")
    public JsonResult<Integer> getFollowPages(HttpServletRequest request){
        try {

            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            Integer userId = userFromRedis.getUserId();

            Integer total = 0;
            Integer size = 10;
            //1.查找所有该用户关注的人
            List<Follow> follows  = userService.getFollowList(userId);
            List<String> followIds = new ArrayList<>();
            for(Follow follow : follows){
                followIds.add(follow.getFollowedUserId()+"");
                System.out.println(follow.getUserId()+"-关注了->"+follow.getFollowedUserId());
            }
            String name = "createTime";  //根据这个索引库的字段来进行排序
            String sortOrder = "DESC";
            for(int page = 1;;page++){
                //获取多条动态
                List<Diary> diaries = diaryService.getFollowsDiaryList(name,page, size, sortOrder,followIds);
                if(diaries == null || diaries.size() == 0){
                    break;
                }else {
                    total += diaries.size();
                }
            }
            return JsonResult.success(total);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取附近的动态列表(进行分页查找，优先进行距离上的升序，再进行时间上的降序查找)   //ps:距离条件还没还没加上
     */
    @GetMapping("/active/getNearActiveList")
    public JsonResult<JSONArray> gettNearActiveList(HttpServletRequest request) {
        try {


//            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String userId = userFromRedis.getUserId()+"";

            String userId = "1";

            JSONArray jsonArray = new JSONArray();   //存储json的结果集
            int page = Integer.parseInt(request.getParameter("page"));  //页数
            int size = Integer.parseInt(request.getParameter("size"));  //一页的数量
            String name = "createTime";  //根据这个索引库的字段来进行排序
            String sortOrder = "DESC";
            //获取多条动态
            List<Diary> diaries = diaryService.gertNearActiveList(name,page, size, sortOrder);
            //输出获取到的动态的时（验证是否是降序）
            for(Diary diary : diaries) {
                System.out.println("create_time —————> " + diary.getCreateTime());
            }

            JsonResult jsonResult = new JsonResult();
            if(diaries != null){

                //遍历动态,获取所有的 diary动态信息、图片、视频、头像添加 进行jsonobject,再添加到jsonArray
                jsonArray = appointmentContro.findPicOrVideo(userId,jsonArray,diaries);

                System.out.println("jsonArray的结果集：");
                System.out.println(jsonArray);
                jsonResult.setResultCode(200);
                jsonResult.setData(jsonArray);
                return jsonResult;
            }else{
                jsonResult.setResultCode(200);
                jsonResult.setData(jsonArray);
                jsonResult.setMessage("没有符合条件的动态数据");
                return jsonResult;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取关注的动态信息
     */
    @GetMapping("/getFollowsDiaryList")
    public JsonResult<JSONArray> getFollowsDiaryList(HttpServletRequest request,@RequestParam("page")Integer page, @RequestParam("size")Integer size)  {
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            Integer userId = userFromRedis.getUserId();
//            Integer userId = 1;

            Integer index = (page - 1) * size;
            //1.查找所有该用户关注的人
//            List<Follow> follows  = userService.getFollowsDiaryList(userId);
            List<Follow> follows  = userService.getFollowList(userId);
            List<String> followIds = new ArrayList<>();
            for(Follow follow : follows){
                followIds.add(follow.getFollowedUserId()+"");
                System.out.println(follow.getUserId()+"-关注了->"+follow.getFollowedUserId());
            }
            String name = "createTime";  //根据这个索引库的字段来进行排序
            String sortOrder = "DESC";
            //获取多条动态
            List<Diary> diaries = diaryService.getFollowsDiaryList(name,page, size, sortOrder,followIds);

            JSONArray jsonArray = new JSONArray();
            if(diaries != null){

                //遍历动态,获取所有的将 diary动态信息、图片、视频、头像添加 进行jsonobject,再添加到jsonArray
                jsonArray = appointmentContro.findPicOrVideo(userId+"",jsonArray,diaries);

                System.out.println("jsonArray的结果集：");
                System.out.println(jsonArray);
                return JsonResult.success(jsonArray);
            }else{
                return JsonResult.success("没有符合条件的数据");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一条动态
     */
    @GetMapping("/active/getOneDiary")
    public JsonResult<JSONObject> getOneDiary(HttpServletRequest request,@RequestParam("diaryId")String diaryId) {
        //            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String userId = userFromRedis.getUserId()+"";
        String userId = "1";


        Diary diary = diaryService.getDiaryByDiaryId(diaryId);
        if(diary != null) {
            JSONArray jsonArray = new JSONArray();
            List<Diary> list = new ArrayList<Diary>();
            list.add(diary);
            appointmentContro.findPicOrVideo(userId,jsonArray, list);
            System.out.println(diaryId + "-->" + diary.getDiaryContent());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            return JsonResult.success(jsonObject);
        }else {
            return JsonResult.success("没有符合条件的数据",null);
        }
    }


    /**
     *  根据动态id获取动态的所有评论
     */
    @GetMapping("/active/getCommentList")
    public JsonResult<JSONArray> getCommentList(HttpServletRequest request,@RequestParam("page") String page,@RequestParam("size") String size,
                                        @RequestParam("diaryId") String diaryId) {
        try {

//            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String userId = userFromRedis.getUserId()+"";
            String userId = "2";

            JSONArray jsonArray = new JSONArray();
            List<Comment> list = new ArrayList<Comment>();
            //根据动态id获取动态的所有评论（在索引库中查找）
            String sortName = "commentCreateTime";   //进行升序依序的索引库中的字段名
            String sortOrder = "DESC";   //ASC表示升序，DESC表示降序
            list = diaryService.getAllComentByDiaryId(page,size, sortName, sortOrder,diaryId);

            for(Comment comment : list){
                System.out.println("————>"+comment.getCommentCreateTime());

                JSONObject jsonObject = new JSONObject();

                User user = userService.getUserByUser_id(comment.getCommentatorIp());
                if(user!=null) {
                    String head_path = user.getHeadPicPath();
                    jsonObject.put("headPicture", head_path);        //头像
                }

                //根据用户id判断用户是否对这条评论点赞了（1-点赞了 0-未点赞）
                UserLikeDiary userLikeDiary = diaryService.getOneLikeDiary(userId,comment.getCommentId());
                if(userLikeDiary!=null) {
                    jsonObject.put("alreadyLike",1);  //点赞了
                }
                else {
                    jsonObject.put("alreadyLike",0);  //未点赞
                }
                jsonObject.put("comment",comment);
                jsonObject.put("Flag",false);

                jsonObject.put("page",1);
                jsonObject.put("replyAmountTemp",comment.getReplyAmount());
                jsonObject.put("flag",false);
                Integer total = 0;   //存储评论的总数量
                for(int p = 1;;p++){
                    List<Comment> commentList = diaryService.getAllComentByDiaryId(p+"",10+"", sortName, sortOrder,diaryId);
                    if (commentList == null || commentList.size() == 0) {
                        break;
                    }
                    total += commentList.size();
                }
                jsonObject.put("total",total);

                List<Reply> replys = new ArrayList<Reply>();
                //根据评论的id 查找其所有的 回复（在索引库中查找）
                String sort = "createTime";   //进行升序依序的索引库中的字段名
                String Order = "ASC";   //ASC表示升序，DESC表示降序
                replys = commentService.getAllReplyByDiaryId("1","2", sort,Order,comment.getCommentId());
                jsonObject.put("children",replys);
                jsonArray.add(jsonObject);
            }
            System.out.println("—————————————————————————————————————获取到的评论队列—————————————————————————————————————————————————————");
            System.out.println(list);
            System.out.println("———————————————————————————————————————————————————————————————————————————————————————————————————————");
            JsonResult jsonResult = new JsonResult();
            jsonResult.setResultCode(200);
            if(jsonArray == null) {
                jsonResult.setData(null);
            } else {
                jsonResult.setData(jsonArray);
            }

            return jsonResult;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 根据评论的id 查找其所有的 回复
     * @param commentId  评论id
     * @return
     */
    @GetMapping("/active/getReplyList")
    public JsonResult<JSONArray> getAllReplyByDiaryId(HttpServletRequest request,@RequestParam("page") String page,@RequestParam("size") String size,
                                            @RequestParam("commentId") String commentId){

        //            //从请求头中获取token
//            String token = request.getHeader("Authorization");
//            //从redis中通过token获取用户信息
//            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
//            String userId = userFromRedis.getUserId()+"";

        String userId = "1";

        JsonResult jsonResult = new JsonResult();
        JSONArray jsonArray = new JSONArray();
        System.out.println("commentId:"+commentId);

        List<Reply> list = new ArrayList<Reply>();

        //根据评论的id 查找其所有的 回复（在索引库中查找）
        String sortName = "createTime";   //进行升序依序的索引库中的字段名
        String sortOrder = "DESC";   //ASC表示升序，DESC表示降序
        list = commentService.getAllReplyByDiaryId(page,size, sortName,sortOrder,commentId);
        for(Reply reply : list){
            System.out.println("-->"+reply.getCreateTime() + "-" +reply.getReplyContent());

            JSONObject jsonObject = new JSONObject();

            User user = userService.getUserByUser_id(reply.getReplyUserId());
            if(user!=null) {
                String head_path = user.getHeadPicPath();
                jsonObject.put("headPicture", head_path);        //头像
            }

            //根据用户id判断用户是否对这条评论点赞了（1-点赞了 0-未点赞）
            UserLikeDiary userLikeDiary = diaryService.getOneLikeDiary(userId,reply.getReplyId());
            if(userLikeDiary!=null) {
                jsonObject.put("alreadyLike",1);  //点赞了
            }
            else {
                jsonObject.put("alreadyLike",0);  //未点赞
            }
            jsonObject.put("comment",reply);
            jsonArray.add(jsonObject);
            jsonResult.setResultCode(200);
            if(jsonArray == null) {
                jsonResult.setData(null);
            } else {
                jsonResult.setData(jsonArray);
            }
        }
        return jsonResult;
    }

    //修改动态的是否可见状态
    @PutMapping("/active/updateLookStatus")
    public JsonResult update(@RequestParam("diaryId") String diaryId,@RequestParam("status") String status){
        System.out.println("-----------------------------");
        diaryService.updateDiaryStatus(diaryId,status);
        rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_INSERT_KEY, diaryId);
        return JsonResult.success("已更新");
    }

    /**
     * 用户自己删除动态
     *      1.删除数据库中的动态信息
     *          同步索引库删除动态信息
     *      2.删除数据库中的动态的所有评论
     *          同步索引库删除评论
     *      3.删除数据库中的所有回复
     *          同步删除索引库中的回复
     *      4.删除数据库中的配图
     *          同步索引库删除配图
     *      5.删除数据库中的视频
     *          同步索引库删除视频
     *      6.删除数据库中的点赞
     */
    @DeleteMapping("/active/deleteDiary")
    public JsonResult<Object> deleteDiary(@RequestParam("diaryId") String diaryId) throws IOException {
        Diary diary = diaryService.getDiaryByDiaryId(diaryId);
        String Did = diary.getId()+"";
        //需要从es中删除，先mq发送求信息
        rabbitTemplate.convertAndSend(MqConstants.DIARY_EXCAHGNE,MqConstants.DIARY_DELETE_KEY, Did);
        diaryService.deleteDiary(diaryId);   //从数据库中删除动态

        List<Comment> commentList = commentService.getCommentByDiaryId(diaryId);
        if(commentList.size()!=0 && commentList != null)
            for(Comment comment : commentList){  //需要从es中删除，先mq发送求信息
                rabbitTemplate.convertAndSend(MqConstants.COMMENT_EXCAHGNE,MqConstants.COMMENT_DELETE_KEY, comment.getId()+"");
                diaryService.deleteAllLikeRecord(comment.getCommentId());  //删除用户对其的点赞记录
            }
        commentService.deleteCommments(diaryId);   //从数据库中删除动态的所有评论

        List<Reply> replyList = commentService.getRepliesByDiaryId(diaryId);
        if(replyList.size()!=0 && replyList != null) {
            for (Reply reply : replyList) {  //需要从es中删除，先Mq发从请求信息
                rabbitTemplate.convertAndSend(MqConstants.REPLY_EXCAHGNE, MqConstants.REPLY_DELETE_KEY, reply.getId() + "");
                diaryService.deleteAllLikeRecord(reply.getReplyId());     //删除用户对其的点赞记录
            }
        }
        commentService.deleteReplies(diaryId);  //从数据库中删除动态的所有回复

        if(diary.getDiaryKind() == 1) {   //图文动态
            List<Picture> pictureList = pictureService.getPicturesByDiaryId(diaryId);
            if (pictureList.size() != 0 && pictureList != null)
                for (Picture picture : pictureList) {  //需要从es中删除，先Mq发从请求信息
                    rabbitTemplate.convertAndSend(MqConstants.PICTURE_EXCAHGNE, MqConstants.PICTURE_DELETE_KEY, picture.getId() + "");
                }
            pictureService.deletePictures(diaryId);  //从数据库中删除动态的所有配图
        }else if(diary.getDiaryKind() == 2) {  //视频动态
            //索引库中中找到动态的视频
            Video video = videoService.getDiaryVideos(diary.getDiaryId());
            if (video != null) {
                System.out.println("------->" + video);
                System.out.println("112-2-21-421-4   //从数据库中删除动态视频  4-24-21-4-124-");
                System.out.println("--->"+video.getVideoId());
                String massage = video.getVideoId();
                //索引库同步删除 mq有问题
                rabbitTemplate.convertAndSend(MqConstants.DIARYVIDEO_EXCAHGNE, MqConstants.DIARYVIDEO_DELETE_KEY, massage);
                videoService.deleteDiaryVideo(diary.getDiaryId());   //从数据库中删除动态视频
                //从索引库中删除视频
                System.out.println("--- 从索引库中删除视频 ---");
            }
        }

        //从数据库中删除对这个的点赞的记录
        diaryService.deleteAllLikeRecord(diary.getDiaryId());

        //删除用户收藏的记录
        userService.deleteCollectByLikeId(diary.getDiaryId());

        return JsonResult.success("成功删除");
    }


    //用户自己删除评论
    @DeleteMapping("/active/deleteComment")
    @Transactional(rollbackFor = Exception.class)    //表示的是该方法无论抛出什么异常都会进行自动回滚
    public JsonResult deleteComment(HttpServletRequest request,@RequestParam("commentId") String commentId){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";

            //删除评论
            Comment comment = commentService.getCommentById(commentId);
            if(comment!=null)  //需要从es中删除，先Mq发从请求信息
                rabbitTemplate.convertAndSend(MqConstants.COMMENT_EXCAHGNE,MqConstants.COMMENT_DELETE_KEY, comment.getId()+"");
            commentService.deleteCommmentByCommenId(commentId);

            //删除该该评论的所有回复
            List<Reply> replyList = commentService.getRepliesByDiaryId(comment.getDiaryId());
            if(replyList.size()!=0 && replyList != null)
                for(Reply reply : replyList){  //需要从es中删除，先Mq发从请求信息
                    rabbitTemplate.convertAndSend(MqConstants.REPLY_EXCAHGNE,MqConstants.REPLY_DELETE_KEY, reply.getId()+"");
                }
            commentService.deleteReplies(comment.getDiaryId());   //从数据库中删除评论

            //从数据库中删除所有有关该评论的点赞记录
            diaryService.deleteUserLike(commentId);

            return JsonResult.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //用户自己删除评论的回复
    @DeleteMapping("/active/deleteReply")
    public JsonResult deleteReply(@RequestParam("replyId") String replyId){
        Reply reply = commentService.getReplyById(replyId);
        if(reply!=null)  //需要从es中删除，先Mq发从请求信息
            rabbitTemplate.convertAndSend(MqConstants.REPLY_EXCAHGNE,MqConstants.REPLY_DELETE_KEY, reply.getId()+"");
        commentService.deleteReplyByReplyId(replyId);   //从数据库中删除回复
        //从数据库中删除所有有关该回复的点赞记录
        diaryService.deleteUserLike(replyId);
        return JsonResult.success();
    }


}
