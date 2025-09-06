package com.mall.job.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Configuration
public class QuartzConfig {
    @Bean
    public ThreadPoolExecutor quartzThreadPoolExecutor(QuartzProperties properties) {
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueSize()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}