package com.life.square.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.life.square.common.JsonResult;
import com.life.square.common.R;
import com.life.square.common.RestClient;
import com.life.square.constants.IndexLibraryConstants;
import com.life.square.pojo.*;
import com.life.square.service.IDiaryService;
import com.life.square.service.IUserService;
import com.life.square.service.IVideoService;
import com.life.square.service.UserCollectionService;
import com.life.square.utils.GetUserFromRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人的内容
 */
@RestController
@RequestMapping("/mine")
public class MineController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GetUserFromRedisUtil userFromRedisUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private RestClient restClient;

    @Autowired
    private IDiaryService diaryService;

    @Autowired
    private appointmentController appointmentContro;

    @Autowired
    private UserCollectionService userCollectionService;

    @Autowired
    private IVideoService videoService;

    /**
     * 获取自己的动态
     */
    @GetMapping("/getMyDiary")
    public JsonResult<JSONArray> getMyDiaryList(HttpServletRequest request, @RequestParam("page")Integer page, @RequestParam("size") Integer size){
        try {
            System.out.println("获取自己的动态,getMyDiary");
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            System.out.println( token );
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//        String userId = "1";


            JSONArray jsonArray = new JSONArray();
            List<Diary> diaryList = new ArrayList<Diary>();
            diaryList = diaryService.getMyDiary(userId,page,size);
            jsonArray = appointmentContro.findPicOrVideo(userId,jsonArray,diaryList);
            return JsonResult.success(jsonArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取自己收藏的 视频 、 图片秀
     */
    @GetMapping("/getMyCollect")
    public JsonResult<JSONArray> getMyCollect(HttpServletRequest request,@RequestParam("page") Integer page, @RequestParam("size")Integer size){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
            //        String userId = "1";

            Boolean isVip = appointmentContro.isVip(userId);
            JSONArray jsonArray = new JSONArray();
            //1.得到用户
            List<UserCollection> collections = new ArrayList<UserCollection>();
            collections = userCollectionService.getUserCollections(userId,page,size);

            for(UserCollection userCollection : collections){
                JSONObject jsonObject = new JSONObject();
                String user_id = userCollection.getLikedId();
                //视频动态类
                if(userCollection.getType() == 1){
                    Diary diary = diaryService.getDiaryByDiaryId(user_id);
                    String name = "diaryId";
                    List<String> videoPaths = videoService.getPathById(name,user_id);
                    String path = "";
                    if(videoPaths != null && videoPaths.size() > 0) {
                        path = videoPaths.get(0);
                    }
                    jsonObject.put("type",1);
                    jsonObject.put("diary",diary);
                    jsonObject.put("videoPath",path);
                }else {
                    appointmentContro.getPictureShowInfo(jsonObject,user_id,isVip,userId);
                    jsonObject.put("type",2);
                }
                jsonArray.add(jsonObject);
            }

            return JsonResult.success(jsonArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关注 or 取消关注用户
     */
    @PostMapping("/concernUser")
    public JsonResult concernUser(HttpServletRequest request,@RequestParam("concernedUserId") String concernedUserId){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            JsonResult jsonResult = new JsonResult();
            String userId = userFromRedis.getUserId()+"";
//        String userId = "1";

            //加判断，不可以关注自己
            if(userId.equals(concernedUserId)){
                jsonResult.setResultCode(505);
                jsonResult.setMessage("【505：不可以关注自己】");
            }

            //查询用户关注的记录
            Follow follow =userService.getFollowRecord(userId,concernedUserId);
            if(follow!=null){
                userService.cancelConcernUser(userId,concernedUserId);
                String msg = "已取消关注";
                return JsonResult.success(msg);
            }
            userService.concernUser(userId,concernedUserId);
            String msg = "关注成功";
            return JsonResult.success(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取消关注用户
     */
    @DeleteMapping("/cancelConcernUser")
    public JsonResult cancelConcernUser(HttpServletRequest request,@RequestParam("concernedUserId") String concernedUserId){
        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//        String userId = "1";

            userService.cancelConcernUser(userId,concernedUserId);
            return JsonResult.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询喜欢人列表 
     */
    @GetMapping("/getLikeUsers")
    public R getLikeUsers(@RequestParam("userId") String userId,@RequestParam("page") Integer page,@RequestParam("size") Integer size){
        try {

            Integer index = (page - 1)*size;

            R r = new R();
            JSONArray jsonArray = new JSONArray();
            List<String> likeUsers = new ArrayList<String>();
            likeUsers = userService.getLikeUserIdsByPage(userId,index,size);
            System.out.println("用户"+userId+"喜欢的人有："+likeUsers);
//            List<User> likeUserList = new ArrayList<User>();

            List<String> likeUserIds = userService.getLikeUserIds(userId);
            Integer pages = 0;
            if(likeUserIds!=null)
                pages = (likeUserIds.size()+size-1)/size;

            for(String likedUserId : likeUsers) {
                String userName = "";
                String userid = "";
                String headPicture = "";
                String introduce = "";

                JSONObject jsonObject = new JSONObject();
                User user = userService.getUserByUser_id(likedUserId);
                BasicInfo basicInfo = userService.getBasicInfoByUserId(likedUserId);
//                likeUserList.add(user);
                if(user!=null){
                    userid = user.getUserId()+"";
                    userName = user.getLoginName();
                    headPicture = user.getHeadPicPath();
                }
                if(basicInfo != null){
                    introduce = basicInfo.getPersonIntro();
                }
                jsonObject.put("userName",user.getLoginName());
                jsonObject.put("userId",user.getUserId());
                jsonObject.put("headPicture",user.getHeadPicPath());
                jsonObject.put("introduce",basicInfo.getPersonIntro());

                jsonArray.add(jsonObject);
            }
            Map<String,Integer> map = new HashMap<>();
            map.put("pages",pages);
            map.put("pages",pages);
            r.setMap(map);
            r.setResultCode(200);
            r.setData(jsonArray);
            String s = "【用户喜欢的用户id】";
            r.setMsg(s);

            return  r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    String s = "【likeUsers：是用户喜欢的用户id】 【likedToo：被喜欢的用户id】 【isNotFriend：彼此喜欢但不是彼此聊天好友，需要自动创建聊天】";


    /**
     * 查询不喜欢人列表
     */
    @GetMapping("/getDisLikeUsers")
    public R getDisLikeUsers(@RequestParam("userId") String userId,@RequestParam("page") Integer page,@RequestParam("size") Integer size){
        try {

            Integer index = (page - 1)*size;

            R r = new R();
            JSONArray jsonArray = new JSONArray();

            List<String> disLikeUserIds = userService.getDisLikeUserIds(userId);
            Integer pages = 0;
            if(disLikeUserIds!=null)
                pages = (disLikeUserIds.size()+size-1)/size;

            List<String> disLikeUsers = new ArrayList<String>();
            disLikeUsers = userService.getDisLikeUsersByPage(userId,index,size);
            System.out.println("用户"+userId+"不喜欢的人有："+disLikeUsers);
//            List<User> likeUserList = new ArrayList<User>();


            for(String dislikeId : disLikeUsers) {
                String userName = "";
                String userid = "";
                String headPicture = "";
                String introduce = "";

                JSONObject jsonObject = new JSONObject();
                User user = userService.getUserByUser_id(dislikeId);
                BasicInfo basicInfo = userService.getBasicInfoByUserId(dislikeId);
//                likeUserList.add(user);
                if(user!=null){
                    userid = user.getUserId()+"";
                    userName = user.getLoginName();
                    headPicture = user.getHeadPicPath();
                }
                if(basicInfo != null){
                    introduce = basicInfo.getPersonIntro();
                }
                jsonObject.put("userName",user.getLoginName());
                jsonObject.put("userId",user.getUserId());
                jsonObject.put("headPicture",user.getHeadPicPath());
                jsonObject.put("introduce",basicInfo.getPersonIntro());

                jsonArray.add(jsonObject);
            }
            Map<String,Integer> map = new HashMap<>();
            map.put("pages",pages);
            map.put("pages",pages);
            r.setMap(map);

            r.setResultCode(200);
            r.setData(jsonArray);
            String s = "【用户不喜欢的用户id】";
            r.setMsg(s);

            return  r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 被喜欢人列表
     */
    @GetMapping("/getLikeMeUserIds")
    public R getLikeMeUserIds(@RequestParam("userId") String userId,@RequestParam("page") Integer page,@RequestParam("size") Integer size){
        R r = new R();
        JSONArray jsonArray = new JSONArray();
        Integer index = (page - 1 )* size;

        //搜出所有的用户的总数
        Integer all = userService.getLikeMeUserIdAmount(userId);
        System.out.println("all:"+all);
        Integer pages = (all + size -1)/size;

        //查询喜欢我的用户id
        List<String> likeMeUserIds = userService.getLikeMeUserIds(userId,index,size);
        List<User> likeMeUserIdList = new ArrayList<User>();
        for(String likedMeUserId : likeMeUserIds) {
            User user = userService.getUserByUser_id(likedMeUserId);
//            likeMeUserIdList.add(user);
            String userName = "";
            String userid = "";
            String headPicture = "";
            String introduce = "";

            JSONObject jsonObject = new JSONObject();
            BasicInfo basicInfo = userService.getBasicInfoByUserId(likedMeUserId);
//                likeUserList.add(user);
            if(user!=null){
                userid = user.getUserId()+"";
                userName = user.getLoginName();
                headPicture = user.getHeadPicPath();
            }
            if(basicInfo != null){
                introduce = basicInfo.getPersonIntro();
            }
            jsonObject.put("userName",user.getLoginName());
            jsonObject.put("userId",user.getUserId());
            jsonObject.put("headPicture",user.getHeadPicPath());
            jsonObject.put("introduce",basicInfo.getPersonIntro());

            jsonArray.add(jsonObject);
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("pages",pages);
        map.put("pages",pages);
        r.setMap(map);
        r.setResultCode(200);
        r.setData(jsonArray);
        r.setMsg("喜欢我的用户id");
        System.out.println(likeMeUserIdList);
        return r;
    }



}
