package com.life.square.controller;


import com.alibaba.fastjson.JSONObject;
import com.life.square.common.JsonResult;
import com.life.square.pojo.Advertising;
import com.life.square.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 2.右滑喜欢人数 vip无限  非vip 15人
 * 3.反悔功能   vip 能反悔 非vip 不能
 * 4.查看其他用户个人信息 非vip 不能查看 图片 和mv
 */

@RestController
@RequestMapping("/advertising")
public class AdvertisingController {

    @Autowired
    private AdvertisingService advertisingService;

    /**
     * 获取广告的列表（按照序号进行升序查询）
     */
    @GetMapping("/getAdvertisingList")
    public JsonResult<JSONObject> getAdvertisingList(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        Integer index = (page-1)*size;
        Integer total =advertisingService.list().size();   //广告的总数量
        List<Advertising> list = advertisingService.getAdvertisingList(index,size);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",total);
        jsonObject.put("list",list);
        System.out.println(jsonObject);
        return JsonResult.success(jsonObject);
    }


}
