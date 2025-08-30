package com.mall.common.domain.cache;

import com.mall.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/6
 */
public final class CaptchaCacher {
    public static String CACHE_KEY = "captcha:";

    private CaptchaCacher() {};

    public static void save(String uuid, String code, Long expiration) {
        RedisUtil.set(
                CACHE_KEY + uuid,
                code,
                expiration,
                TimeUnit.SECONDS
        );
    }

    public static String get(String uuid) {
        return RedisUtil.get(CACHE_KEY + uuid);
    }
}
