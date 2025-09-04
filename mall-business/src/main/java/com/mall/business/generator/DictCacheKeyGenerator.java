package com.mall.business.generator;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
public class DictCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() + "_" + StringUtils.arrayToDelimitedString(params,
                                                                                            "_");
    }
}
