package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_disable_date_user_collect")
public class UserCollection {
    private Integer id;
    private String userId;
    private String likedId;  //动态的id or 图片秀的用户id
    private Integer type;   //1-视频动态 2-用户的图片秀
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date createTime;
}
