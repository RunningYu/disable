package com.life.square.utils;

import com.life.square.advise.exception.TokenNotFoundException;
import com.life.square.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Slf4j
@Configuration
public class GetUserFromRedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public User getUserFromRedis(String token) throws IOException {
        if (token == null) {
            throw new TokenNotFoundException("No Authorization");
        }
        Object user_str = redisTemplate.opsForValue().get(token);
        if (user_str == null) {
            throw new TokenNotFoundException("No Authorization");
        }
        log.info("用户信息: #{}", user_str);
        return (User) JsonUtil.jsonToObj((String) user_str, User.class);
    }
}
