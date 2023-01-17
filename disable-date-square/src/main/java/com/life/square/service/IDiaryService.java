package com.life.square.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.Comment;
import com.life.square.pojo.Diary;
import com.life.square.pojo.Follow;
import com.life.square.pojo.UserLikeDiary;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDiaryService extends IService<Diary> {

    //动态的评论
    void insertComment(Integer id,String comment_id, String comment, String diary_id, String commentator_ip,String commentator_name,Integer is_deleted);

    //添加动态信息
    void insertDiary(Integer id, String diary_id, String diary_user_id, String diary_user_name,String diary_title, String diary_content, Integer diaryKind,Integer diary_status, String enable_comment);

    //该动态的评论数+1
    void commentAddOne(String diary_id);

    //通过diaryId查询动态
    Diary getDiaryByDiaryId(String diaryId);

    //根据动态diaryId查询所有的评论
    List<Comment> getAllComentByDiaryId(String page, String size, String sortName, String sortOrder, String diaryId) throws IOException;

    //查询表里所有的评论
    List<Comment> getAll();

    //查询多条动态信息
    List<Diary> gertNearActiveList(String name,int page,int size,String sortOrder) throws IOException;

    //根据id将新增的diary插入（到索引库）
    void insertDiaryToIndexById(String id);

    //根据id将要删除的diary从索引库中删除
    void deleteDiaryFromIndexById(String diaryId);

    //根据动态dirayId找出所有的配图并新增到索引库
    void insertPicToIndexById(String diaryId);

    //添加评论的回复到回复表
    void insertReply(Integer id, String reply_id,String comment_id, String diary_id,String reply_content, String reply_user_id, String reply_user_name, String comment_user_id, String comment_user_name);

    //给对应的动态的回复量 +1
    void commentReplyAmountAddOne(String comment_id);

    //从数据库中删除动态
    void deleteDiary(String diaryId);

    //修改动态是否可见的状态status
    void updateDiaryStatus(String diaryId, String status);

    //获取关注的人的动态列表
    List<Diary> getFollowsDiaryList(String name, Integer page, Integer size, String sortOrder, List<String> follows);

    //用交集的用户id去从索引库搜索出所有的动态信息，并且进行时间的降序排序
    List<Diary> getVideoDiariesByUserId(List<String> idList, Integer page, Integer size);


    //获取非交集id的用户动态
    List<Diary> getDiariesByNotUserId(List<String> idList, Map<String,String> map,Integer page, Integer size);

    //视频秀的搜索框搜索功能(通过输入关键字搜索)  匹对的字段 ： 动态标题 、 动态内容
    List<Diary> getVideoDiaryByMatchKeyWords(Set<String> keywords, Integer page, Integer size);

    //获取自己的所有动态列表
    List<Diary> getMyDiary(String userId,Integer page,Integer size);

    //动态收藏量+1
    void addDiaryCollectAmount(String likedId);

    //动态收藏量-1
    void subDiaryCollectAmount(String likedId);

    //存储用户点赞的记录
    void addLikeDiaryRecord(Integer id,String userId, String diaryId);

    //从数据库中获取一条用户点赞动态的记录信息
    UserLikeDiary getOneLikeDiary(String userId, String diaryId);

    //动态点赞量+1
    void addDiaryLikeAmount(String diaryId);

    //动态点赞量-1
    void subDiaryLikeAmount(String diaryId);

    //删除点赞记录
    void deleteLikeDiaryRecord(String userId, String diaryId);

    //根据收藏的对象id从数据库中删除所有有关的记录
    void deleteUserLike(String likedId);

    //从数据库中删除对这个的点赞的记录
    void deleteAllLikeRecord(String diaryId);

    //获取用户点赞过的视频id
    List<String> getCollectedDiaryIds(String userId);
}
