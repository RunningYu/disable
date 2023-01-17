package com.life.square.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.BasicInfo;
import com.life.square.pojo.BasicInfoDoc;
import com.life.square.pojo.DetailInfo;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface InfoService extends IService<BasicInfo> {

    //通过id来查询个人 基本信息
    BasicInfo getByPersonId(int i);

    //通过距离范围获取
    List<JSONObject> getBasicInfoByDistance(String index, String filed, String location, String distance);

    //从索引库中获取一个用户的基本信息
    BasicInfoDoc getOneUserBasicInfoById(String userId) throws IOException;

    //从数据库中查询所的用户的详细的信息
    List<DetailInfo> getAllDetail();

    //从索引库中查询出所有爱好匹对的用户的详细信息
    List<DetailInfo> getAllUserDetailFromIndexMatchEsname(String index,String esName,Set<String> hobbiesSet);

//    //从索引库中查询出所有爱好匹对的用户的详细信息
    List<DetailInfo> getAllUserDetailFromIndexMatchHobby(Set<String> hobbiesSet);

    //1.根据 年龄范围、距离范围、性别 筛选出所有的用户基本信息
    List<BasicInfoDoc> getBasicInfosByAgeDistanceSex(Integer age1,Integer age2,Integer distance,Integer sex);

    Integer getAmount();
}
