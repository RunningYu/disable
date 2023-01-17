package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户报名表
 */
@Data
@TableName("tb_disable_date_admin_activity_register")
public class ActivityRegister{
    private Integer id;
    private Integer userId;
    private Integer activityId;
    private String userPhone;
    private Integer status;
    private String ps;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date createTime;

}
