package com.life.square.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.dao.AdvertisingMapper;
import com.life.square.pojo.Advertising;
import com.life.square.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingMapper, Advertising> implements AdvertisingService {

    @Autowired
    private AdvertisingMapper advertisingMapper;

    //获取广告的列表（按照序号进行升序查询）
    public List<Advertising> getAdvertisingList(Integer index, Integer size) {
        return advertisingMapper.getAdvertisingList( index,  size);
    }
}
