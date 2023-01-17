package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.Advertising;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdvertisingMapper extends BaseMapper<Advertising> {

    //获取广告的列表（按照序号进行升序查询）
    @Select("select * from tb_disable_date_admin_advertising ORDER by ordinal ASC limit #{index},#{size}")
    List<Advertising> getAdvertisingList(@Param("index") Integer index, @Param("size") Integer size);
}
