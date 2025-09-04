package com.mall.web.config;

import com.mall.business.generator.DictCacheKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
@Configuration
public class CacheConfig {
    @Bean
    KeyGenerator dictCacheKeyGenerator() {
        return new DictCacheKeyGenerator();
    }
}
