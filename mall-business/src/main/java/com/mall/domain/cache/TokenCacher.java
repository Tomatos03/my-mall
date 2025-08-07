package com.mall.domain.cache;

import com.mall.properties.JwtProperties;
import com.mall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/6
 */
@Component
public class TokenCacher {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisUtil redisUtil;

    public static String CACHE_KEY = "token:";


    public void save(String username, String token) {
        redisUtil.set(
                CACHE_KEY + username,
                token,
                jwtProperties.getExpiration(),
                TimeUnit.SECONDS
        );
    }

    public void del(String username) {
        redisUtil.del(CACHE_KEY + username);
    }

    public String getTokenFromCache(String username) {
        return redisUtil.get(CACHE_KEY + username);
    }
}
