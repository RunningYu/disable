package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_disable_users_like_videos")
public class LikeVideos {
    private Integer id;
    private String userId;
    private String diaryId;
}
