package com.life.square.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.pojo.Report;
import com.life.square.service.ReportService;
import com.life.square.dao.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Autowired
    private ReportMapper reportMapper;


    public void insertReport(Integer id,String userId, String reportedUserId, String reason, String reportedId, Integer type) {
        reportMapper.insertReport( id,userId,  reportedUserId,  reason,  reportedId,  type);
    }
}
