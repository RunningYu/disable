package com.life.square.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.life.square.service.FestivalService;
import com.life.square.dao.FestivalMapper;
import com.life.square.pojo.Festival;
import org.springframework.stereotype.Service;

@Service
public class FestivalServiceImpl extends ServiceImpl<FestivalMapper, Festival> implements FestivalService {
}
