package com.life.square.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.common.RestClient;
import com.life.square.dao.LikeHobbyMapper;
import com.life.square.pojo.LikeHobby;
import com.life.square.service.LikeHobbyService;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeHobbyServiceImpl extends ServiceImpl<LikeHobbyMapper, LikeHobby> implements LikeHobbyService {

    @Autowired
    private RestClient restClient;

    //获取出一个用户的所有收藏过的视频的兴趣爱好
    public List<LikeHobby> getUserLikeHobbisByUserId(String userId) {
        try {
            String index = "tb_disable_date_user_like_hobby";
            String name = "userId";
            SearchHit[] hits = restClient.boolQuery_termQuery_getAllByOneText(index,name,userId);
            List<LikeHobby> likeHobbies = new ArrayList<LikeHobby>();
            //反序列
            Integer n = 0;
            List<LikeHobby> list = new ArrayList<LikeHobby>();
            for(SearchHit hit : hits){
                JSONObject jsonObject = new JSONObject();
                n++;
                //获取文档source
                String json = hit.getSourceAsString();
                //反序列化
                LikeHobby likeHobby = JSON.parseObject(json,LikeHobby.class);
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                System.out.println("获取到第 "+n+" 个结果 --> "+likeHobby);
                System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————————");
                list.add(likeHobby);
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
