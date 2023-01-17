package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.Comment;
import com.life.square.pojo.Diary;
import com.life.square.pojo.UserLikeDiary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryMapper extends BaseMapper<Diary> {

    //动态的评论
    @Insert("insert into tb_disable_date_diary_comment(id,comment_id,comment_body,diary_id,commentator_ip," +
            "commentator_name,is_deleted) " +
            "values (#{id},#{comment_id},#{comment},#{diary_id},#{commentator_ip},#{commentator_name},#{is_deleted})")
    void insertComment(@Param("id") Integer id,@Param("comment_id") String comment_id, @Param("comment") String comment,
                       @Param("diary_id") String diary_id,@Param("commentator_ip")String commentator_ip, @Param("commentator_name") String commentator_name,
                       @Param("is_deleted") Integer is_deleted);

    //添加动态信息
    @Insert("insert into tb_disable_date_diary(id,diary_id,diary_user_id,diary_user_name,diary_title,diary_content,diary_kind,diary_status,enable_comment)" +
            "values (#{id},#{diary_id},#{diary_user_id},#{diary_user_name},#{diary_title},#{diary_content},#{diaryKind},#{diary_status},#{enable_comment})")
    void insertDiary(@Param("id") Integer id, @Param("diary_id") String diary_id, @Param("diary_user_id") String diary_user_id,
            @Param("diary_user_name")String diary_user_name,@Param("diary_title")  String diary_title,
            @Param("diary_content") String diary_content, @Param("diaryKind") Integer diaryKind,@Param("diary_status") Integer diary_status,
                     @Param("enable_comment") String enable_comment);

    //该动态的评论数+1
    @Update("UPDATE tb_disable_date_diary SET diary_comment = diary_comment+1 WHERE diary_id = #{diary_id}")
    void commentAddOne(String diary_id);

    //根据id查询diary
    @Select("select * from tb_disable_date_diary where diary_id = #{diaryId}")
    Diary getDiaryById(String diaryId);

    //查询表里所得所有评论
    @Select("select * from tb_disable_date_diary_comment")
    List<Comment> getAll();

    //添加评论的回复到回复表
    @Insert("insert into tb_disable_date_diary_reply(id, reply_id,comment_id, diary_id,reply_content, reply_user_id, reply_user_name, " +
            "comment_user_id, comment_user_name) values (#{id},#{reply_id},#{comment_id},#{diary_id},#{reply_content}," +
            "#{reply_user_id},#{reply_user_name},#{comment_user_id},#{comment_user_name})")
    void insertReply(@Param("id") Integer id, @Param("reply_id") String reply_id,@Param("comment_id") String comment_id, @Param("diary_id") String diary_id,
                     @Param("reply_content") String reply_content, @Param("reply_user_id") String reply_user_id,
                     @Param("reply_user_name") String reply_user_name, @Param("comment_user_id") String comment_user_id,
                     @Param("comment_user_name") String comment_user_name);

    //从数据库中删除动态
    @Delete("delete from tb_disable_date_diary WHERE diary_id = #{diaryId}")
    void deleteDiary(String diaryId);

    //修改动态是否可见的状态status
    @Update("update tb_disable_date_diary SET enable_look = #{status} WHERE diary_id = #{diaryId}")
    void updateDiaryStatus(@Param("diaryId") String diaryId, @Param("status") String status);

    //动态收藏量+1
    @Update("update tb_disable_date_diary SET diary_collect = diary_collect + 1 WHERE diary_id = #{likedId}")
    void addDiaryCollectAmount(String likedId);

    //动态收藏量-1
    @Update("update tb_disable_date_diary SET diary_collect = diary_collect - 1 WHERE diary_id = #{likedId}")
    void subDiaryCollectAmount(String likedId);

    //存储用户点赞的记录
    @Insert("insert into tb_disable_date_user_like_diary(id,user_id,diary_id) values (#{id},#{userId},#{diaryId})")
    void addLikeDiaryRecord(@Param("id") Integer id,@Param("userId") String userId,@Param("diaryId") String diaryId);

    //从数据库中获取一条用户点赞动态的记录信息
    @Select("select * from tb_disable_date_user_like_diary WHERE user_id = #{userId} and diary_id = #{diaryId}")
    UserLikeDiary getOneLikeDiary(@Param("userId") String userId,@Param("diaryId") String diaryId);

    //动态点赞量+1
    @Update("update tb_disable_date_diary SET diary_love = diary_love + 1 WHERE diary_id = #{diaryId}")
    void addDiaryLikeAmount(String diaryId);

    //动态点赞量-1
    @Update("update tb_disable_date_diary SET diary_love = diary_love - 1 WHERE diary_id = #{diaryId}")
    void subDiaryLikeAmount(String diaryId);

    //删除点赞记录
    @Delete("delete FROM tb_disable_date_user_like_diary WHERE user_id = #{userId} and diary_id = #{diaryId} ")
    void deleteLikeDiaryRecord(@Param("userId") String userId,@Param("diaryId") String diaryId);

    //根据收藏的对象id从数据库中删除所有有关的记录
    @Delete("delete FROM tb_disable_date_user_like_diary WHERE diary_id = #{likedId}")
    void deleteUserLike(String likedId);

    //从数据库中删除对这个的点赞的记录
    @Delete("delete FROM tb_disable_date_user_like_diary WHERE diary_id = #{diaryId}")
    void deleteAllLikeRecord(String diaryId);
}
