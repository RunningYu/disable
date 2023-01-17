package com.life.square.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.life.square.pojo.Report;

public interface ReportService extends IService<Report> {


    void insertReport(Integer id,String userId,String reportedUserId,String reason,String reportedId,Integer type);

}
