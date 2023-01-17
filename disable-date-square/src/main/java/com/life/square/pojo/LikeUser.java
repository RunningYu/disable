package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 喜欢用户的记录表
 */
@Data
@TableName("tb_disable_date_likeuser")
public class LikeUser {
    private Integer id;
    private String userId;
    private String likedUserId;
    private Date createTime;
}
