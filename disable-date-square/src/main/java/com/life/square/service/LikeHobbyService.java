package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.LikeHobby;

import java.util.List;

public interface LikeHobbyService extends IService<LikeHobby> {

    //获取出一个用户的所有收藏过的视频的兴趣爱好
    List<LikeHobby> getUserLikeHobbisByUserId(String userId);
}
