package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 记录用户点赞动态的
 * tb_disable_date_user_like_diary
 */
@Data
@TableName("tb_disable_date_user_like_diary")
public class UserLikeDiary {
    private Integer id;
    private String userId;
    private String diaryId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;   //点赞的时间
}
