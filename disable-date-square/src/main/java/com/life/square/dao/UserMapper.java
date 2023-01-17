package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into tb_disable_date_user_collect(id,user_id,liked_id,type) values (#{id},#{user_id},#{liked_id},#{type})")
    void collect(@Param("id")Integer id, @Param("user_id") String user_id, @Param("liked_id") String liked_id,@Param("type") Integer type);

    //取消指定喜欢的图片秀id
    @Delete("delete from tb_disable_date_user_collect where user_id = #{user_id} and liked_id = #{liked_id}")
    void cancel_collect(@Param("user_id") String user_id, @Param("liked_id") String liked_id);

    //添加不喜欢的用户关联
    @Insert("insert into tb_disable_date_dislike(id,user_id,dislike_user_id) values (#{id},#{user_id},#{dislike_user_id})")
    void dislikeUser(@Param("id") int id, @Param("user_id") String user_id, @Param("dislike_user_id") String dislike_user_id);

    //取消不喜欢的用户
    @Delete("delete from tb_disable_date_dislike where user_id = #{user_id} and dislike_user_id = #{dislike_user_id}")
    void cancel_dislikeUser(@Param("user_id") String user_id, @Param("dislike_user_id") String dislike_user_id);

    //收藏视频秀（来源于广场中的视频类的动态）
    @Insert("insert into tb_disable_users_like_videos(id,user_id,diary_id) values(#{id},#{userId},#{diaryId}) ")
    void collectDiaryVideo(@Param("id") Integer id,@Param("userId") String userId,@Param("diaryId") String diaryId);

    //取消收藏视频秀（来源于广场中的视频类的动态）
    @Delete("delete from tb_disable_users_like_videos WHERE user_id = #{userId} and diary_id = #{diaryId} ")
    void CancelCollectDiaryVideo(@Param("userId") String userId,@Param("diaryId") String diaryId);

    //获取到用户喜欢的视频关系记录 id
    @Select("select * from tb_disable_users_like_videos WHERE user_id = #{userId} and diary_id = #{diaryId}")
    LikeVideos getLikeVideos(@Param("userId") String userId, @Param("diaryId") String diaryId);

    //获取所有关注的关系信息
    @Select("select * from tb_disable_date_follow")
    List<Follow> getFollws();

    @Select("select * from tb_disable_date_user WHERE user_id = #{userId}")
    User getUserByUser_id(String userId);

    //用交集的用户id去从数据库中找出用户个人相册
    @Select("select * from tb_disable_picture_album WHERE user_id = #{userId}")
    List<Album> getAlbumByUserId(String userId);

    //从数据库中获取出用户的详细信息
    @Select("select * from tb_disable_date_person_detail_info WHERE person_id = #{userId}")
    DetailInfo getDetailInfoByUserId(String user_id);

    //从数据库中获取出用户的基本信息
    @Select("select * from tb_disable_date_person_basic_info WHERE person_id = #{userId}")
    BasicInfo getBasicInfoByUserId(String user_id);

    //关注
    @Insert("insert into tb_disable_date_follow(user_id,followed_user_id) values (#{userId},#{concernedUserId})")
    void concernUser(@Param("userId") String userId, @Param("concernedUserId") String concernedUserId);

    //取消关注用户
    @Delete("delete FROM tb_disable_date_follow WHERE user_id = #{userId} and followed_user_id = #{concernedUserId}")
    void cancelConcernUser(@Param("userId") String userId, @Param("concernedUserId") String concernedUserId);

    //删除用户收藏的记录
    @Delete("delete FROM tb_disable_date_user_collect WHERE liked_id = #{likedId}")
    void deleteCollectByLikeId(String likedId);

    //从数据库中查询MV
    @Select("select * from tb_disable_videos WHERE user_id = #{user_id}")
    MV getMVById(String user_id);

    //喜欢用户
    @Insert("insert into tb_disable_date_likeuser (user_id,liked_user_id) values (#{userId},#{likedUserId})")
    void insertLikeUser(@Param("userId") String userId,@Param("likedUserId") String likedUserId);

    //取消喜欢用户
    @Delete("delete from tb_disable_date_likeuser WHERE user_id = #{userId} and liked_user_id = #{likedUserId}")
    void cancelLikeUser(@Param("userId") String userId,@Param("likedUserId") String likedUserId);

    //获取用户喜欢的人的所有id
    @Select("select * from tb_disable_date_likeuser WHERE user_id = #{userId} ")
    List<LikeUser> getLikeUserIds(String userId);

    //获取用户不喜欢的所有id
    @Select("select * from tb_disable_date_dislike WHERE user_id = #{userId}")
    List<DislikeUser> getDisLikeUserIds(String userId);

    //获取用户关注的所有id
    @Select("select * from tb_disable_date_follow WHERE user_id = #{userId}")
    List<Follow> getConcernUserIds(String userId);

    //获取喜欢用户记录
    @Select("select * from tb_disable_date_likeuser WHERE user_id = #{userId} and liked_user_id = #{likedUserId}")
    LikeUser getLikeUser(@Param("userId") String userId,@Param("likedUserId") String likedUserId);

    //查询用户关注的记录
    @Select("select * from tb_disable_date_follow WHERE user_id = #{userId} and followed_user_id = #{concernedUserId}")
    Follow getFollowRecord(@Param("userId") String userId,@Param("concernedUserId") String concernedUserId);

    //查找用户聊天好友
    @Select("select * from tb_disable_date_chat_friends WHERE user_id = #{userId} and fuser_id = #{likedUserId}")
    ChatFriend findChatFriendRecord(@Param("userId") String userId,@Param("likedUserId") String likedUserId);

    //查询喜欢我的用户id
    @Select("select * from tb_disable_date_likeuser WHERE liked_user_id = #{userId} limit #{index},#{size}")
    List<LikeUser> getLikeMeUserIds(@Param("userId") String userId,@Param("index")Integer index,@Param("size")Integer size);

    //创建好友关系
    @Insert("insert into tb_disable_date_chat_friends(user_id,fuser_id) values (#{userId},#{likedUserId})")
    void insertChatFriend(@Param("userId") String userId,@Param("likedUserId") String likedUserId);

    //分页查询自己喜欢的人的id
    @Select("select * from tb_disable_date_likeuser WHERE user_id = #{userId} limit #{index},#{size}")
    List<LikeUser> getLikeUserIdsByPage(@Param("userId") String userId,@Param("index")Integer index,@Param("size")Integer size);

    //搜出所有的用户的总数
    @Select("select COUNT(*) FROM tb_disable_date_likeuser WHERE liked_user_id = #{userId}")
    Integer getLikeMeUserIdAmount(String userId);

    //查询不喜欢人列表
    @Select("select * from tb_disable_date_dislike WHERE user_id = #{userId} limit #{index},#{size}")
    List<DislikeUser> getDisLikeUsersByPage(@Param("userId") String userId,@Param("index")Integer index,@Param("size")Integer size);

    //从数据库中查询自己关注的用户
    @Select("select * from tb_disable_date_follow WHERE user_id = #{userId}")
    List<Follow> getFollwList(Integer userId);
}
