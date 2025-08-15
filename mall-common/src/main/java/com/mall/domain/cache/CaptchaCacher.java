package com.mall.domain.cache;

import com.mall.properties.CaptchaProperties;
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
public class CaptchaCacher {
    public static String CACHE_KEY = "captcha:";
    @Autowired
    private CaptchaProperties captchaProperties;
    @Autowired
    private RedisUtil redisUtil;

    public void save(String uuid, String code) {
        redisUtil.set(
                CACHE_KEY + uuid,
                code,
                captchaProperties.getExpiration(),
                TimeUnit.SECONDS
        );
    }

    public String get(String uuid) {
        return redisUtil.get(CACHE_KEY + uuid);
    }
}
