package com.mall.job.quartz;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Data
@Component
@ConfigurationProperties("my-mall.quartz")
public class QuartzProperties {
    /**
     * 核心线程数
     */
    private int corePoolSize = 8;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 10;

    /**
     * 队列大小
     */
    private int queueSize = 200;

    /**
     * 空闲线程回收时间，多少秒
     */
    private int keepAliveSeconds = 30;

    /**
     * 线程前缀
     */
    private String threadNamePrefix = "QuartzThread";
}