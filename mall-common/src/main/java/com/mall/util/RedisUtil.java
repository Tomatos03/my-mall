package com.mall.util;

import cn.hutool.core.util.StrUtil;
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
    StringRedisTemplate stringRedisTemplate;

    /**
     * 保存缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return true;
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


    /**
     * 删除缓存
     *
     * @param key 存储缓存时的key
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/5 15:57
     */
    public void del(String key) {
        try {
            if (!StrUtil.isEmpty(key)) {
                stringRedisTemplate.delete(key);
            }
        } catch (Exception e) {
            log.error("Redis删除数据失败：", e);
        }
    }

    /**
     * 保存缓存（仅当键不存在时设置）
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setNX(String key, String value) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 保存缓存（仅当键不存在时设置），并设置过期时间（单位秒）
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间（秒）
     * @return true成功 false失败
     */
    public boolean setNX(String key, String value, long expireTime) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 保存缓存（仅当键不存在时设置），并设置指定单位的过期时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @param unit       时间单位
     * @return true成功 false失败
     */
    public boolean setNX(String key, String value, long expireTime, TimeUnit unit) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expireTime, unit);
        return Boolean.TRUE.equals(result);
    }
}
