package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户关注的信息表
 */
@Data
@TableName("/tb_disable_date_follow")
public class Follow {

    private Integer id;
    private Integer userId;   //用户id
    private Integer followedUserId;  //被关注的人的id
    private Integer status;   //关注状态 1-未关注 2-关注 3-拉黑
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;  //关系创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;  //关系更新时间
}
