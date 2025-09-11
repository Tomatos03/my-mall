package com.mall.common.config;

import com.mall.common.util.SnowflakeIdWorker;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/11
 */
@Data
@Configuration
public class SnowflakeConfig {
    @Value("${my-mall.id-generator.snowflake.worker-id}")
    private long workerId;

    @Value("${my-mall.id-generator.snowflake.datacenter-id}")
    private long datacenterId;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(workerId, datacenterId);
    }
}
