package com.life.square.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("tb_disable_users_report")
public class Report {
    private Integer id;
    private String userId;
    private String reportedUserId;
    private String reportedId;
    private Integer type;
    private String reason;
    private Integer status;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private String createTime;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private String updateTime;
    private String ps;

}
