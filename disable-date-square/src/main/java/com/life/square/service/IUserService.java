package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.*;

import java.util.List;
import java.util.Set;
/*
创建名为“userServiceImpl”的bean时出错：通过字段“baseMapper”表示的不满意的依赖关系;
嵌套异常是 org.springframework.beans.factory.BeanCreationException：
创建在文件中定义名称“userMapper”的 bean 时出错 Init 方法调用失败;
嵌套异常是 java.lang.IllegalArgumentException： 属性 'sqlSessionFactory' 或 'sqlSessionTemplate' 是必需的
 */

//若封装好的增删改查不能完成我们所需要的操作，我们依然可以在下面进行方法书写，切记不要将方法覆盖
public interface IUserService extends IService<User> {

    //收藏
    void collect(Integer id,String user_id, String liked_id,Integer type);

    //取消指定喜欢的图片秀id
    void cancel_collect(String user_id, String liked_id);

    //添加不喜欢的用户关联
    void dislikeUser(int id, String user_id, String dislike_user_id);

    //取消不喜欢的用户
    void cancel_dislikeUser(String user_id, String dislike_user_id);

    //收藏视频秀（来源于广场中的视频类的动态）
    void collectDiaryVideo(Integer id,String userId, String diaryId);

    //取消收藏视频秀
    void CancelCollectDiaryVideo(String userId, String diaryId);

//    //将用户喜欢的视频关系添加到索引库中
//    void insertLikeVideosToIndexById(String userId,String diaryId);

    //获取到用户喜欢的视频关系记录 id
    LikeVideos getLikeVideos(String userId, String diaryId);

    //根据 用户喜欢的视频关系记录id 从索引库中删除
    void deleteLikeVideosFromIndexById(String id);

    //查找所有该用户关注的人
    List<Follow> getFollowsDiaryList(Integer userId);

    User getUserByUser_id(String userId);

    //用交集的用户id去从数据库中找出用户个人相册
    List<Album> getAlbumByUserId(String userId);

    //从数据库中获取出用户的详细信息
    DetailInfo getDetailInfoByUserId(String user_id);

    //从数据库中获取出用户的基本信息
    BasicInfo getBasicInfoByUserId(String user_id);

    //关键字子搜索 得到 匹对的用户详细信息的（用详细信息的标签，爱好来进行匹对）
    List<DetailInfo> getDetailByMatchKeyWords(Set<String> keywords,Integer max);

    //关注
    void concernUser(String userId, String concerndUserId);

    //取消关注用户
    void cancelConcernUser(String userId, String concernedUserId);

    //删除用户收藏的记录
    void deleteCollectByLikeId(String diaryId);

    //从数据库中查询MV
    MV getMVById(String user_id);

    //喜欢用户
    void insertLikeUser(String userId, String likedUserId);

    //取消喜欢用户
    void cancelLikeUser(String userId, String likedUserId);

    //获取用户喜欢的人的所有id
    List<String> getLikeUserIds(String userId);

    //获取用户不喜欢的所有id
    List<String> getDisLikeUserIds(String userId);

    //获取用户关注的所有id
    List<String> getConcernUserIds(String userId);

    //获取喜欢用户记录
    LikeUser getLikeUser(String userId, String likedUserId);

    //查询用户关注的记录
    Follow getFollowRecord(String userId, String concernedUserId);

    //查找用户聊天好友
    ChatFriend findChatFriendRecord(String userId, String likedUserId);

    //查询喜欢我的用户id
    List<String> getLikeMeUserIds(String userId,Integer page,Integer size);

    //创建好友关系
    void insertChatFriend(String userId, String likedUserId);

    //分页查询自己喜欢的人的id
    List<String> getLikeUserIdsByPage(String userId, Integer index, Integer size);

    //搜出所有的用户的总数
    Integer getLikeMeUserIdAmount(String userId);

    //查询不喜欢人列表
    List<String> getDisLikeUsersByPage(String userId, Integer index, Integer size);

    //根据输入的用户名进行模糊查询
    List<BasicInfo> getUsersByMatchUserName(Set<String> words,Integer max);

    //从数据库中查询自己关注的用户
    List<Follow> getFollowList(Integer userId);
}
