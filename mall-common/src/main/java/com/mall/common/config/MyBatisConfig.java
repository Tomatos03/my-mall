package com.mall.common.config;

import com.mall.common.interceptor.AutoFillInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/12
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public AutoFillInterceptor autoFillInterceptor() {
        return new AutoFillInterceptor();
    }
}
