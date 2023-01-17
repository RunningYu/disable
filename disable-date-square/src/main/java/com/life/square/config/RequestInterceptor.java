package com.life.square.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class RequestInterceptor  implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;

    private List<String> patterns = new ArrayList<String>();  //不用过滤的url

    public void addInterceptor(InterceptorRegistry registry){

        //写一个拦截器
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


                //从请求头中获取token
                String token = request.getHeader("Authorization");
//                                      从redis中获取token
                if(token != null && redisTemplate.opsForValue().get(token) != null){
                    //每次认证后就充值为30天                      时间单位：天
                    redisTemplate.expire(token,30, TimeUnit.DAYS);
                    return true;   //取到就返回true
                }

                //设置响应状态为401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return false;
            }
        }).excludePathPatterns(patterns);  //不用过滤的url

    }
}
