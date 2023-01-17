package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_disable_picture_album")
public class Album {
    private Integer id;
    private Integer userId;
    private String picDesc;
    private String picPath;
    private Integer likeCounts;
    private Integer status;
    private Date createTime;
}
