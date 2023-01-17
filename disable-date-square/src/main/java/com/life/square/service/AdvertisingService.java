package com.life.square.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.Advertising;

import java.util.List;

public interface AdvertisingService extends IService<Advertising> {

    //获取广告的列表（按照序号进行升序查询）
    List<Advertising> getAdvertisingList(Integer index, Integer size);
}
