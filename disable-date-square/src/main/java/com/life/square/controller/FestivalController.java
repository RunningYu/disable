package com.life.square.controller;

import com.life.square.common.JsonResult;
import com.life.square.pojo.Festival;
import com.life.square.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;


    /**
     * 获取一个节日主题（通过id来找）
     */
    @GetMapping("/getFestival")
    public JsonResult<Festival> getFestival(@RequestParam("id") Integer id){
        Festival festival = festivalService.getById(id);
        return JsonResult.success(festival);
    }

}
