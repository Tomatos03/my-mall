package com.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Slf4j
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 保存缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis保存数据失败：", e);
            return false;
        }
    }

    /**
     * 保存缓存，同时设置过期时间(单位秒)
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value, long expireTime) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("Redis保存数据失败：", e);
            return false;
        }
    }

    /**
     * 保存缓存，同时设置指定单位的过期时间
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value, long expireTime, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, expireTime, unit);
            return true;
        } catch (Exception e) {
            log.error("Redis保存数据失败：", e);
            return false;
        }
    }

    /**
     * 获取普通缓存
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }
}
