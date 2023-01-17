package com.life.square.controller;

import com.life.square.pojo.Activity;
import com.alibaba.fastjson.JSONObject;
import com.life.square.common.JsonResult;
import com.life.square.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RedisTemplate redisTemplate;

//    // 测试redis
//    @GetMapping("/redis")
//    public Object redisTest( @RequestParam("key") String key ) {
//        ValueOperations ops = redisTemplate.opsForValue();
//        ops.set( "age", key );          // 设置键值
//        Object obj = ops.get( key );   // 获取redis值
//        System.out.println( obj );
//        return obj;
//    }

    /**
     * 获取活动的列表（按照序号进行升序查询）
     */
    @GetMapping("/getActivityList")
    public JsonResult<JSONObject> getActivityList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Integer total = activityService.list().size();
        Integer index = (page - 1) * size;
        List<Activity> list = activityService.getActivityList(index, size);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("toal", total);
        jsonObject.put("list", list);
        System.out.println(jsonObject);
        return JsonResult.success(jsonObject);
    }

    /**
     * 报名活动
     */
    @PostMapping("/register")
    public JsonResult register(@RequestParam("userId") String userId, @RequestParam("activityId") Integer activityId, @RequestParam("userPhone") String userPhone) {
        Integer id = null;
        activityService.register(id, userId, activityId, userPhone);

        String msg = "已经提交报名，等待审核通过";

        return JsonResult.success(msg);
    }


}
