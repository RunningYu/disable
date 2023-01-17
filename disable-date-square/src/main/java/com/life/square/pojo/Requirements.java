package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * DROP TABLE IF EXISTS `tb_disable_date_requirements`;			#配偶要求
 * CREATE TABLE `tb_disable_date_requirements`  (
 *   `person_id` BIGINT(0) '用户主键id',
 *   `age_range` VARCHAR(10)  '年龄范围',
 *   `height_range` TINYINT(0)  '身高范围',
 *   `marry_status` TINYINT(0)  '婚姻情况 1-未结婚 2-离异 3-已婚',
 *   `education_background` VARCHAR(10)  '教育背景要求',
 *   `income` INT(0)   '收入要求',
 *   `housing_status` VARCHAR(10)  '房子',
 *   `car_status` VARCHAR(10)  '车子',
 *   `other_requirements` VARCHAR(50)  '其他要求',
 *   PRIMARY KEY (`person_id`) USING BTREE
 */
@Data
@TableName("tb_disable_date_requirements")
public class Requirements {

    private Integer personId;
    private String ageRange;
    private String heightRange;
    private Integer marryStatus;
    private String educationBackground;
    private String income;
    private String housingStatus;
    private String carStatus;
    private String otherRequirements;

//    private Integer person_id;
//    private String age_range;
//    private String height_range;
//    private Integer marry_status;
//    private String education_background;
//    private String income;
//    private String housing_status;
//    private String car_status;
//    private String other_requirements;

}
