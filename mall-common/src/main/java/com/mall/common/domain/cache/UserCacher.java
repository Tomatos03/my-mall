package com.mall.common.domain.cache;

import cn.hutool.json.JSONUtil;
import com.mall.dto.AuthenticatedUserDTO;
import com.mall.common.util.RedisUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/6
 */
public final class UserCacher {
    public static String CACHE_KEY = "user:";

    private UserCacher() {};

    public static void save(AuthenticatedUserDTO authenticationUserDTO, Long expiration) {
        String username = authenticationUserDTO.getUsername();
        String userJson = JSONUtil.toJsonStr(authenticationUserDTO);
        RedisUtil.set(
                CACHE_KEY + username,
                userJson,
                expiration,
                TimeUnit.SECONDS
        );
    }

    public static void del(String username) {
        RedisUtil.del(CACHE_KEY + username);
    }

    public static String getUserJson(String username) {
        return RedisUtil.get(CACHE_KEY + username);
    }
}
