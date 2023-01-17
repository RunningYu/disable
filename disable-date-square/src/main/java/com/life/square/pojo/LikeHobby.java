package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_disable_date_user_like_hobby")
public class LikeHobby {
    private Integer id;
    private String userId;
    private String hobby;
}
