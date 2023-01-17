package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * CREATE TABLE `tb_disable_date_person_basic_info`  (
 *   `person_id` BIGINT(0)                      '用户基本信息表主键id',
 *   `person_name` VARCHAR(10)                  'COMMENT '姓名',
 *   `sex` TINYINT(0)                           '性别 1-男 2-女',
 *   `age` INT(0)                               '年龄',
 *   `image_path` VARCHAR(100)                  '人脸图片上传路径',
 *   `disable_number` VARCHAR(20)               '残疾证号码',
 *   `work_addr` VARCHAR(50)                    '工作地区',
 *   `household_addr` VARCHAR(50)               '户籍地区',
 *   `marital_status` TINYINT(0)                '婚姻情况(1-未结婚 2-离异 3-已婚)',
 *   `height` INT(0)                            '身高',
 *   `weight` INT(0)                            '体重',
 *   `degree` VARCHAR(5)                        '最高学历',
 *   `income` INT(0)                            '月收入',
 *   `occupation` VARCHAR(10)                   '职业',
 *   `housing_status` VARCHAR(10)               '住房情况',
 *   `car_status` VARCHAR(10)                   '购车情况 ',
 *   `expected_marry_time` DATETIME(0)          '期待结婚时间',
 *   `person_intro` TEXT                        '自我介绍',
 *   `person_sign` TEXT                         '爱情宣言',
 *   `wechat` VARCHAR(20)                       '微信号 ',
 *   `wechat_code_images_path` VARCHAR(50)      '微信二维码上传路径',
 *   `qq` VARCHAR(13)                           'QQ账号 ',
 *   `email` VARCHAR(30)                        '邮箱',
 *   `phone` VARCHAR(11)                        '电话',
 *
 *   PRIMARY KEY (`person_id`) USING BTREE
 */

@Data
@TableName("tb_disable_date_person_basic_info")
public class BasicInfoDoc {
    private Integer personId;

    private String personName;

    private Integer sex;

    private Integer age;

    private String phone;

    private String workAddr;

    private String householdAddr;

    private String maritalStatus;

    private Integer height;

    private Integer weight;

    private String degree;

    private Integer income;

    private String occupation;

    private String housingStatus;

    private String carStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedMarryTime;

    private String personIntro;

    private String personSign;

    private String location;

    private String wechat;

    private String wechatCodeImagesPath;

    private String qq;

    private String email;

    private String mv;

    public BasicInfoDoc() {

    }

    public BasicInfoDoc(BasicInfo basicInfo) {
        this.personId = basicInfo.getPersonId();
        this.personName = basicInfo.getPersonName();
        this.sex = basicInfo.getSex();
        this.age = basicInfo.getAge();
        this.phone = basicInfo.getPhone();
        this.workAddr = basicInfo.getWorkAddr();
        this.householdAddr = basicInfo.getHouseholdAddr();
        this.maritalStatus = basicInfo.getMaritalStatus();
        this.height = basicInfo.getHeight();
        this.weight = basicInfo.getWeight();
        this.degree = basicInfo.getDegree();
        this.income = basicInfo.getIncome();
        this.occupation = basicInfo.getOccupation();
        this.housingStatus = basicInfo.getHousingStatus();
        this.carStatus = basicInfo.getCarStatus();
        this.expectedMarryTime = basicInfo.getExpectedMarryTime();
        this.personIntro = basicInfo.getPersonIntro();
        this.personSign = basicInfo.getPersonSign();
        this.location = basicInfo.getLatitude() + ", " + basicInfo.getLongitude();  //纬度，经度
        this.wechat = basicInfo.getWechat();
        this.wechatCodeImagesPath = basicInfo.getWechatCodeImagesPath();
        this.qq = basicInfo.getQq();
        this.email = basicInfo.getEmail();
        this.mv = basicInfo.getMv();
    }

}
