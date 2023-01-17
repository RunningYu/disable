package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.BasicInfo;
import com.life.square.pojo.DetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper extends BaseMapper<BasicInfo> {


    //通过id来查询个人 基本信息
    @Select("select * from tb_disable_date_person_basic_info where person_id = #{id}")
    BasicInfo getByPersionId(int id);

    //从数据库中查询所的用户的详细的信息
    @Select("select * from tb_disable_date_person_detail_info")
    List<DetailInfo> getAllDetail();

    @Select("select COUNT(*) FROM tb_disable_date_person_detail_info")
    Integer getAmount();
}
