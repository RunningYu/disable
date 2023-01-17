package com.life.square.controller;

import com.life.square.common.JsonResult;
import com.life.square.common.R;
import com.life.square.pojo.User;
import com.life.square.service.ReportService;
import com.life.square.utils.GetUserFromRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 举报
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private GetUserFromRedisUtil userFromRedisUtil;

    @Autowired
    private ReportService reportService;

    /**
     * 举报
     */
    @PostMapping("/insertReport")
    public JsonResult report(HttpServletRequest request, @RequestParam("reportedUserId")String reportedUserId,
                             @RequestParam("reason") String reason, @RequestParam("reportedId") String reportedId,
                             @RequestParam("type") Integer type){

        try {
            //从请求头中获取token
            String token = request.getHeader("Authorization");
            //从redis中通过token获取用户信息
            User userFromRedis = userFromRedisUtil.getUserFromRedis(token);
            String userId = userFromRedis.getUserId()+"";
//        String userId = "1";

            R r = new R();
//        Report report = new Report();
            Integer id = null;
//        report.setId(id);
//        report.setUserId(userId);
//        report.setReportedId(reportedUserId);
//        report.setReportedId(reportedId);
//        report.setReason(reason);
//        report.setType(type);
            reportService.insertReport(id,userId,reportedUserId,reason,reportedId,type);

            return JsonResult.success("等待投诉信息审核");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


}
