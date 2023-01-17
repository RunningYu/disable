package com.life.square.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.dao.UserMapper;
import com.life.square.pojo.*;
import com.life.square.service.IUserService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/*
创建名为“userServiceImpl”的bean时出错：通过字段“baseMapper”表示的不满意的依赖关系;
嵌套异常是 org.springframework.beans.factory.BeanCreationException：
创建在文件中定义名称“userMapper”的 bean 时出错 Init 方法调用失败;
嵌套异常是 java.lang.IllegalArgumentException： 属性 'sqlSessionFactory' 或 'sqlSessionTemplate' 是必需的
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private RestClient restClient;

    //收藏图片秀或视频秀
    public void collect(Integer id,String user_id, String liked_id,Integer type) {
        userMapper.collect(id,user_id,liked_id,type);
    }

    //取消 收藏图片秀或视频秀
    public void cancel_collect(String user_id, String liked_id) {
        userMapper.cancel_collect( user_id, liked_id);
    }

    //添加不喜欢的用户关联
    public void dislikeUser(int id, String user_id, String dislike_user_id) {
        userMapper.dislikeUser(id, user_id,  dislike_user_id);
    }

    //取消不喜欢的用户
    public void cancel_dislikeUser(String user_id, String dislike_user_id) {
        userMapper.cancel_dislikeUser(user_id,dislike_user_id);
    }


    //收藏视频秀（来源于广场中的视频类的动态）
    public void collectDiaryVideo(Integer id,String userId, String diaryId) {
        userMapper.collectDiaryVideo(id, userId,  diaryId);
    }

    //取消收藏视频秀（来源于广场中的视频类的动态）
    public void CancelCollectDiaryVideo(String userId, String diaryId) {
        userMapper.CancelCollectDiaryVideo( userId,  diaryId);
    }


    //获取到用户喜欢的视频关系记录 id
    public LikeVideos getLikeVideos(String userId, String diaryId) {
        return userMapper.getLikeVideos( userId,  diaryId);
    }

    //根据 用户喜欢的视频关系记录id 从索引库中删除
    public void deleteLikeVideosFromIndexById(String id) {
        String index = "tb_disable_users_like_videos";
        restClient.DeleteDocument(index,id);
    }

//    //将用户喜欢的视频关系添加到索引库中
//    public void insertLikeVideosToIndexById(String userId,String diaryId) {
//        try {
//            // 根据id查询酒店数据
//            LikeVideos likeVideos = getLikeVideos(userId,diaryId);
//
//            //1.准备Request对象
//            IndexRequest request = new IndexRequest("tb_disable_users_like_videos").id(likeVideos.getId().toString());
//            //2.准备Json文档
//            request.source(JSON.toJSONString(likeVideos), XContentType.JSON);
//            //3.发送请求
//            client.index(request, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    //查找所有该用户关注的人
    public List<Follow> getFollowsDiaryList(Integer userId) {
        try {
            List<Follow> list = new ArrayList<Follow>();
            String index = "tb_disable_date_follow";
            String name = "userId";
            SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,name,userId+"");
            int n = 0;
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                Follow follow = JSON.parseObject(json,Follow.class);
                list.add(follow);
//                System.out.println("------------------------------------------------------------------------");
//                System.out.println("获取到第 "+n+" 个结果 --> "+follow);
//                System.out.println("------------------------------------------------------------------------");
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUser_id(String userId) {
        return userMapper.getUserByUser_id(userId);
    }

    //用交集的用户id去从数据库中找出用户个人相册
    public List<Album> getAlbumByUserId(String userId) {
        return userMapper.getAlbumByUserId(userId);
    }

    //从数据库中获取出用户的详细信息
    public DetailInfo getDetailInfoByUserId(String user_id) {
        return userMapper.getDetailInfoByUserId(user_id);
    }

    //从数据库中获取出用户的基本信息
    public BasicInfo getBasicInfoByUserId(String user_id) {
        return userMapper.getBasicInfoByUserId(user_id);
    }

    //关键字子搜索 得到 匹对的用户详细信息的（用详细信息的标签，爱好来进行匹对）
    public List<DetailInfo> getDetailByMatchKeyWords(Set<String> keywords,Integer max) {

        String index = "tb_disable_date_person_detail_info";
        List<String> esName = new ArrayList<String>();
        esName.add("hobby");        //爱好
        esName.add("habit");        //作息习惯
        esName.add("personTag");    //个人标签
//        SearchHit[] hits = restClient.getDetailByMatchKeyWords(index,keywords,esName);
        SearchHit[] hits = restClient.getDetailByMatchKeyWords(index,keywords,esName,max);
        int n = 0;
        List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
        for(SearchHit hit : hits){
            n++;
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            DetailInfo detailInfo = JSON.parseObject(json,DetailInfo.class);
            detailInfoList.add(detailInfo);
                System.out.println("------------------------------------------------------------------------");
                System.out.println("获取到第 "+n+" 个结果 --> 爱好【"+detailInfo.getHobby() +"】  生活习惯【" +detailInfo.getHabit()
                +"】 个人标签【"+detailInfo.getPersonTag()+"】");
                System.out.println("------------------------------------------------------------------------");
        }

        return detailInfoList;
    }

    //关注
    public void concernUser(String userId, String concernedUserId) {
        userMapper.concernUser(userId,concernedUserId);
    }

    //取消关注用户
    public void cancelConcernUser(String userId, String concernedUserId) {
        userMapper.cancelConcernUser(userId,concernedUserId);
    }

    //删除用户收藏的记录
    public void deleteCollectByLikeId(String likedId) {
        userMapper.deleteCollectByLikeId(likedId);
    }

    //从数据库中查询MV
    public MV getMVById(String user_id) {
        return userMapper.getMVById(user_id);
    }

    //喜欢用户
    public void insertLikeUser(String userId, String likedUserId) {
        userMapper.insertLikeUser( userId, likedUserId);
    }

    //取消喜欢用户
    public void cancelLikeUser(String userId, String likedUserId) {
        userMapper.cancelLikeUser( userId,  likedUserId);
    }

    //获取用户喜欢的人的所有id
    public List<String> getLikeUserIds(String userId) {
        List<LikeUser> likeUsers = userMapper.getLikeUserIds(userId);
        List<String> list = new ArrayList<String>();
        if(likeUsers!=null) {
            for (LikeUser likeUser : likeUsers) {
                list.add(likeUser.getLikedUserId());
            }
        }
        return list;
    }

    //获取用户不喜欢的所有id
    public List<String> getDisLikeUserIds(String userId) {
        List<DislikeUser> dislikeUsers = userMapper.getDisLikeUserIds(userId);
        List<String> list = new ArrayList<String>();
        if(dislikeUsers!=null) {
            for (DislikeUser dislikeUser : dislikeUsers) {
                list.add(dislikeUser.getDislikeUserId());
            }
        }
        return list;
    }

    //获取用户关注的所有id
    public List<String> getConcernUserIds(String userId) {
        List<Follow> followUserIds = userMapper.getConcernUserIds(userId);
        List<String> list = new ArrayList<String>();
        if(followUserIds!=null) {
            for (Follow follow : followUserIds) {
                list.add(follow.getFollowedUserId()+"");
            }
        }
        return list;
    }

    //获取喜欢用户记录
    public LikeUser getLikeUser(String userId, String likedUserId) {
        return userMapper.getLikeUser(userId,likedUserId);
    }

    //查询用户关注的记录
    public Follow getFollowRecord(String userId, String concernedUserId) {
        return userMapper.getFollowRecord(userId,concernedUserId);
    }

    //查找用户聊天好友
    public ChatFriend findChatFriendRecord(String userId, String likedUserId) {
        return userMapper.findChatFriendRecord( userId,  likedUserId);
    }

    //查询喜欢我的用户id
    public List<String> getLikeMeUserIds(String userId,Integer index,Integer size) {
        List<LikeUser> likeUsers = userMapper.getLikeMeUserIds(userId,index,size);
        List<String> likeMeUserIds = new ArrayList<String>();
        for(LikeUser likeUser : likeUsers){
            likeMeUserIds.add(likeUser.getUserId());
        }
        return likeMeUserIds;
    }

    //创建好友关系
    public void insertChatFriend(String userId, String likedUserId) {
        userMapper.insertChatFriend(userId,likedUserId);
    }

    //分页查询自己喜欢的人的id
    public List<String> getLikeUserIdsByPage(String userId, Integer index, Integer size) {
        List<LikeUser> likeUsers = userMapper.getLikeUserIdsByPage( userId,  index,  size);
        List<String> list = new ArrayList<>();
        for(LikeUser likeUser : likeUsers){
            list.add(likeUser.getLikedUserId());
        }
        return list;
    }

    //搜出所有的用户的总数
    public Integer getLikeMeUserIdAmount(String userId) {
        Integer all = userMapper.getLikeMeUserIdAmount(userId);
        return all;
    }

    //查询不喜欢人列表
    public List<String> getDisLikeUsersByPage(String userId, Integer index, Integer size) {
        List<DislikeUser> dislikeUsers = userMapper.getDisLikeUsersByPage( userId,  index,  size);
        List<String> list = new ArrayList<>();
        for(DislikeUser dislikeUser : dislikeUsers){
            list.add(dislikeUser.getDislikeUserId());
        }

        return list;
    }

    //根据输入的用户名进行模糊查询
    public List<BasicInfo> getUsersByMatchUserName(Set<String> keyWords,Integer max) {

        try {
//            String index = "disable-date-user";         //es中用户表
            String index = "disable-date-basic-info";
            List<String> esName = new ArrayList<>();
            esName.add("personName");
            SearchHit[] hits = restClient.getDetailByMatchKeyWords(index,keyWords,esName,max);
            List<BasicInfo> list = new ArrayList<BasicInfo>();
            int n = 0;
            for(SearchHit hit : hits){
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                BasicInfo basicInfo = JSON.parseObject(json,BasicInfo.class);
                list.add(basicInfo);
                System.out.println("------------------------------------------------------------------------");
                System.out.println("获取到第 "+n+" 个用户基本结果 --> "+basicInfo);
                System.out.println("------------------------------------------------------------------------");
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //从数据库中查询自己关注的用户
    public List<Follow> getFollowList(Integer userId) {
        List<Follow> follows = userMapper.getFollwList(userId);
        return follows;
    }


}
