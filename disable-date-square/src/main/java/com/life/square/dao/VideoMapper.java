package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.Video;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {


    //添加视频
    @Insert("insert into tb_disable_diary_videos(id,video_id,diary_id,user_id,video_path) values (#{id},#{videoId},#{diary_id},#{diary_user_id},#{name})")
    void insertVideo(@Param("id") Integer id,@Param("videoId") String videoId,@Param("diary_id") String diary_id,@Param("diary_user_id")String diary_user_id,@Param("name") String name);

    //获取一个动态视频
    @Select("select * from tb_disable_diary_videos WHERE video_id = #{videoId}")
    Video getDiaryVideoById(String videoId);

    //从数据库中删除动态视频
    @Delete("delete from tb_disable_diary_videos WHERE diary_id = #{diaryId}")
    void deleteDiaryVideo(String diaryId);
}
