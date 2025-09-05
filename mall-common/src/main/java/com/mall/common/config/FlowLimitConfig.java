package com.mall.common.config;

import com.mall.common.aspect.limit.FlowLimitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Configuration
public class FlowLimitConfig {
    @Bean
    public DefaultRedisScript<Long> FlowLimitScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("/lua/flow_limit.lua"));
        script.setResultType(Long.class);
        return script;
    }

    @Bean
    public FlowLimitAspect flowLimitAspect() {
        return new FlowLimitAspect();
    }
}
