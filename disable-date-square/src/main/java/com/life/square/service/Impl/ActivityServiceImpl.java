package com.life.square.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.service.ActivityService;
import com.life.square.dao.ActivityMapper;
import com.life.square.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    //获取活动的列表（按照序号进行升序查询）
    @Override
    public List<Activity> getActivityList(Integer index, Integer size) {
        return activityMapper.getActivityList(index, size);
    }

    //报名活动
    @Override
    public void register(Integer id, String userId, Integer activityId, String userPhone) {
        activityMapper.register(id, userId,  activityId,  userPhone);
    }
}




