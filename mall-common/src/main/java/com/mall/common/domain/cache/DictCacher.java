package com.mall.common.domain.cache;

import com.mall.common.util.RedisUtil;

import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
public final class DictCacher {
    private DictCacher() {};

    private final static String CACHE_KEY = "dictData";

    public static void saveMap(Map<String, String> map) {
        RedisUtil.hset(CACHE_KEY, map);
    }

    public static String getDictDetailListJson(String hashKey) {
        return RedisUtil.hget(CACHE_KEY, hashKey)
                        .toString();
    }
}
