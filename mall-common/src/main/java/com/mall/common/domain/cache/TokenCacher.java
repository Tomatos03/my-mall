package com.mall.common.domain.cache;

import com.mall.common.util.RedisUtil;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/6
 */
public final class TokenCacher {
    public static String CACHE_KEY = "token:";

    private TokenCacher() {};

    public static void save(String username, String token, Long expiration) {
        RedisUtil.set(
                CACHE_KEY + username,
                token,
                expiration,
                TimeUnit.SECONDS
        );
    }

    public static void del(String username) {
        RedisUtil.del(CACHE_KEY + username);
    }

    public static String getTokenFromCache(String username) {
        return RedisUtil.get(CACHE_KEY + username);
    }
}
