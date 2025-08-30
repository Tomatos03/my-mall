package com.mall.common.domain.cache;

import com.mall.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
public final class RequestCacher {
    private final static long EXPIRATION = 3L;
    private final static String CACHE_KEY = "repeatSubmit:";
    private final static String LOCK = "1";

    private RequestCacher() {};

    public static boolean save(String requestInfo) {
        return RedisUtil.setNX(CACHE_KEY + requestInfo, LOCK, EXPIRATION);
    }
}
