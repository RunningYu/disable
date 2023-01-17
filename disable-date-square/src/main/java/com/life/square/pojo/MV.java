package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 个人 MV
 * DROP TABLE IF EXISTS `tb_disable_videos`;
 * CREATE TABLE `tb_disable_videos`  (
 *   `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
 *   `user_id` bigint(0) NOT NULL COMMENT '发布者id',
 *   `audio_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户使用音频的信息',
 *   `video_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频描述',
 *   `video_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '视频存放的路径',
 *   `video_seconds` float(6, 2) NULL DEFAULT NULL COMMENT '视频秒数',
 *   `video_width` int(0) NULL DEFAULT NULL COMMENT '视频宽度',
 *   `video_height` int(0) NULL DEFAULT NULL COMMENT '视频高度',
 *   `cover_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频封面图',
 *   `like_counts` bigint(0) NOT NULL DEFAULT 0 COMMENT '喜欢/赞美的数量',
 *   `status` int(0) NOT NULL COMMENT '视频状态：1-发布成功 2-未过审，管理员操作',
 *   `create_time` datetime(0) NOT NULL COMMENT '创建时间',
 *   PRIMARY KEY (`id`) USING BTREE
 * ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频信息表' ROW_FORMAT = Dynamic;
 */
@Data
@TableName("tb_disable_videos")
public class MV {
    private String id;
    private Integer userId;
    private String audioId;
    private String videoDesc;
    private String videoPath;
    private Float videoSeconds;
    private Integer videoWidth;
    private Integer videoHeight;
    private String coverPath;
    private Integer likeCounts;
    private Integer status;
    private Date createTime;
}
