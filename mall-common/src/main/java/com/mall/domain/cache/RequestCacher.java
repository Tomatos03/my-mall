package com.mall.domain.cache;

import com.mall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
@Component
public class RequestCacher {
    private final static long EXPIRATION = 3L;
    private final static String CACHE_KEY = "repeatSubmit:";
    private final static String LOCK = "1";

    @Autowired
    private RedisUtil redisUtil;

    public boolean save(String requestInfo) {
        return redisUtil.setNX(CACHE_KEY + requestInfo, LOCK, EXPIRATION);
    }
}
