package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.UserCollection;

import java.util.List;

public interface UserCollectionService extends IService<UserCollection> {
    //获取自己收藏的 视频 、 图片秀
    List<UserCollection> getUserCollections(String userId, Integer page, Integer size);

    //添加用户的收藏（ 视频 、 图片秀）到索引库中
    void insertLikeUserCollectToIndexById(String userId, String likedId);

    //从索引库中删除用户的收藏（ 视频 、 图片秀）
    void deleteCollectFromIndexById(String id);

    //获取单个收藏
    UserCollection getUserCollection(String userId, String likedId);
}
