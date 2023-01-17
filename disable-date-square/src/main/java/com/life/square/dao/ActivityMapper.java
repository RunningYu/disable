package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.Activity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    //获取活动的列表（按照序号进行升序查询）
    @Select("select * from tb_disable_date_admin_activity ORDER by number ASC limit #{index},#{size}")
    List<Activity> getActivityList(@Param("index") Integer index, @Param("size") Integer size);

    //报名活动
    @Insert("insert into tb_disable_date_admin_activity_register(id,user_id,activity_id,user_phone)" +
            "values(#{id},#{userId},#{activityId},#{userPhone}) ")
    void register(@Param("id") Integer id, @Param("userId") String userId, @Param("activityId") Integer activityId, @Param("userPhone") String userPhone);

}
