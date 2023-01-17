package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.Activity;

import java.util.List;

public interface ActivityService extends IService<Activity> {

    //获取活动的列表（按照序号进行升序查询）
    List<Activity> getActivityList(Integer index, Integer size);

    //报名活动
    void register(Integer id,String userId, Integer activityId, String userPhone);
}
