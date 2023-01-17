package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.Picture;

import java.io.IOException;
import java.util.List;

public interface IPictureService extends IService<Picture> {

    //添加照片到照片表
    void insertPicture(Integer id,String diary_id, String name, String diary_user_id, String pic_path, Integer status);

    //根据id去找图像路径
    List<String> getPathById(String name, String id) throws IOException;


    void insertPicToIndexById(String diaryId);

    //从数据库中删除动态的所有配图
    void deletePictures(String diaryId);

    //从数据库中查找出动态的所有配图
    List<Picture> getPicturesByDiaryId(String diaryId);

    //从索引库中删除动态的所有配图
    void deleteDiaryFromIndexById(String message);
}
