package com.mall.domain.cache;

import cn.hutool.json.JSONUtil;
import com.mall.dto.AuthenticatedUserDTO;
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
public class UserCacher {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisUtil redisUtil;

    public static String CACHE_KEY = "user:";

    public void save(AuthenticatedUserDTO authenticationUserDTO) {
        String username = authenticationUserDTO.getUsername();
        String userJson = JSONUtil.toJsonStr(authenticationUserDTO);
        redisUtil.set(
                CACHE_KEY + username,
                userJson,
                jwtProperties.getExpiration(),
                TimeUnit.SECONDS
        );
    }

    public void del(String username) {
        redisUtil.del(CACHE_KEY + username);
    }

    public String getUserJson(String username) {
        return redisUtil.get(CACHE_KEY + username);
    }
}
