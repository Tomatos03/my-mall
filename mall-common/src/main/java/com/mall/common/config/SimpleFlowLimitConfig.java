package com.mall.common.config;

import com.mall.common.aspect.limit.SimpleFlowLimitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Configuration
public class SimpleFlowLimitConfig {
    @Bean
    public SimpleFlowLimitAspect simpleFlowLimitAspect() {
        return new SimpleFlowLimitAspect();
    }
}
