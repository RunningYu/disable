package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.Video;

import java.util.List;


public interface IVideoService extends IService<Video> {

    //从索引库中获取视频的路径
    List<String> getPathById(String name, String diaryId);

    void insertVideo(Integer id, String videoId,String diary_id, String diary_user_id,String name);

    //同步动态的视频到索引库
    void insertDiaryVideosToIndexById(String message);

    //同步删除动态的视频
    void deleteDiaryVideosFromIndexById(String message);

    //索引库中中找到动态的视频
    Video getDiaryVideos(String diaryId);

    //从数据库中删除动态视频
    void deleteDiaryVideo(String diaryId);

    //获取一个动态视频
    Video getDiaryVideoById(String videoId);
}
