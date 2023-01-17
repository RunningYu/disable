package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * CREATE TABLE `tb_disable_date_user`  (
 * `user_id` BIGINT(0) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
 * `nick_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
 * `login_name` VARCHAR(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
 * `password_md5` VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
 * `is_deleted` TINYINT(0) NOT NULL DEFAULT 0 COMMENT '注销标识字段(1-正常 2-未认证 3-已注销)',
 * `locked_flag` TINYINT(0) NOT NULL DEFAULT 0 COMMENT '锁定标识字段(1-未锁定 2-已锁定)',
 * `create_time` DATETIME(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
 * PRIMARY KEY (`user_id`) USING BTREE
 * ) ENGINE = INNODB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;
 */
@Data//将 数据库中的tb_disable_date_user 数据表和 User 实体进行映射
@TableName("tb_disable_date_user")
public class User {
    Integer userId;

    String nickName;

    String loginName;

    String passwordMd5;

    Integer sex;

    Integer age;

    Integer isVip;

    Integer love;

    Integer likes;

    Integer sorts;

    String disableNumber;

    String headPicPath;

    String onlineTime;

    Integer isDeleted;

    Integer lockedFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnoreProperties(ignoreUnknown = true)
    java.util.Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnoreProperties(ignoreUnknown = true)
    java.util.Date updateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnoreProperties(ignoreUnknown = true)
    java.util.Date lastTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnoreProperties(ignoreUnknown = true)
    java.util.Date expirationTime;

    public User() {
    }

    public User(Integer userId, String nickName, String loginName, String passwordMd5, Integer sex, Integer age, Integer isVip, Integer love, Integer likes, Integer sorts, String disableNumber, String headPicPath, String onlineTime, Integer isDeleted, Integer lockedFlag, java.util.Date createTime, java.util.Date updateTime, java.util.Date lastTime, java.util.Date expirationTime) {
        this.userId = userId;
        this.nickName = nickName;
        this.loginName = loginName;
        this.passwordMd5 = passwordMd5;
        this.sex = sex;
        this.age = age;
        this.isVip = isVip;
        this.love = love;
        this.likes = likes;
        this.sorts = sorts;
        this.disableNumber = disableNumber;
        this.headPicPath = headPicPath;
        this.onlineTime = onlineTime;
        this.isDeleted = isDeleted;
        this.lockedFlag = lockedFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.lastTime = lastTime;
        this.expirationTime = expirationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getLockedFlag() {
        return lockedFlag;
    }

    public void setLockedFlag(Integer lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getSorts() {
        return sorts;
    }

    public void setSorts(Integer sorts) {
        this.sorts = sorts;
    }

    public String getDisableNumber() {
        return disableNumber;
    }

    public void setDisableNumber(String disableNumber) {
        this.disableNumber = disableNumber;
    }

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public java.util.Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(java.util.Date lastTime) {
        this.lastTime = lastTime;
    }

    public java.util.Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", passwordMd5='" + passwordMd5 + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", isVip=" + isVip +
                ", love=" + love +
                ", likes=" + likes +
                ", sorts=" + sorts +
                ", disableNumber='" + disableNumber + '\'' +
                ", headPicPath='" + headPicPath + '\'' +
                ", onlineTime='" + onlineTime + '\'' +
                ", isDeleted=" + isDeleted +
                ", lockedFlag=" + lockedFlag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lastTime=" + lastTime +
                ", expirationTime=" + expirationTime +
                '}';
    }
}

