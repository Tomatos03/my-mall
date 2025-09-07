package com.mall.common.util;

import cn.hutool.core.util.StrUtil;
import com.mall.common.context.SpringBeanHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Slf4j
public final class RedisUtil {
    private RedisUtil(){};

    public static StringRedisTemplate stringRedisTemplate() {
        return SpringBeanHolder.getBean("stringRedisTemplate", StringRedisTemplate.class);
    }

    /**
     * 保存缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, String value) {
        stringRedisTemplate().opsForValue().set(key, value);
        return true;
    }

    /**
     * 保存缓存，同时设置过期时间(单位秒)
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, String value, long expireTime) {
        try {
            stringRedisTemplate().opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
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
    public static boolean set(String key, String value, long expireTime, TimeUnit unit) {
        try {
            stringRedisTemplate().opsForValue().set(key, value, expireTime, unit);
            return true;
        } catch (Exception e) {
            log.error("Redis保存数据失败：", e);
            return false;
        }
    }

    public static boolean hset(String cacheKey, Map<?, ?> map) {
        try {
            stringRedisTemplate().opsForHash().putAll(cacheKey, map);
            return true;
        } catch (Exception e) {
            log.error("Redis保存数据失败：", e);
            return false;
        }
    }

    public static boolean hset(String key, String field, String value) {
        try {
            stringRedisTemplate().opsForHash().put(key, field, value);
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
    public static String get(String key) {
        return key == null ? null : stringRedisTemplate().opsForValue().get(key);
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
    public static void del(String key) {
        try {
            if (!StrUtil.isEmpty(key)) {
                stringRedisTemplate().delete(key);
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
    public static boolean setNX(String key, String value) {
        Boolean result = stringRedisTemplate().opsForValue()
                                              .setIfAbsent(key, value);
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
    public static boolean setNX(String key, String value, long expireTime) {
        Boolean result = stringRedisTemplate().opsForValue()
                                              .setIfAbsent(
                                                      key,
                                                      value,
                                                      expireTime,
                                                      TimeUnit.SECONDS
                                              );
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
    public static boolean setNX(String key, String value, long expireTime, TimeUnit unit) {
        Boolean result = stringRedisTemplate().opsForValue()
                                              .setIfAbsent(
                                                      key,
                                                      value,
                                                      expireTime,
                                                      unit
                                              );
        return Boolean.TRUE.equals(result);
    }

    public static Object hget(String cacheKey, String hashkey) {
        return stringRedisTemplate().opsForHash().get(cacheKey, hashkey);
    }
}
