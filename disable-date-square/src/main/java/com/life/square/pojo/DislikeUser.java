package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 不喜欢用户的记录信息表
 */
@Data
@TableName("tb_disable_date_dislike")
public class DislikeUser {
    private Integer id;
    private String userId;
    private String dislikeUserId;
}
