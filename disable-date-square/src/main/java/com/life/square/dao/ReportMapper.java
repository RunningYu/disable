package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {

    @Insert("insert into tb_disable_users_report(id,user_id,reported_user_id,reason,reported_id,type) values (#{id},#{userId}," +
            "#{reportedUserId},#{reason},#{reportedId},#{type})")
    void insertReport(@Param("id")Integer id,@Param("userId") String userId,@Param("reportedUserId") String reportedUserId,
                      @Param("reason") String reason, @Param("reportedId") String reportedId, @Param("type") Integer type);
}
