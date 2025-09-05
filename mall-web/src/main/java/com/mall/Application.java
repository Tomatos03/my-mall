package com.mall;

import com.mall.common.annotation.enable.EnableFlowLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@EnableFlowLimit // 开启分布式限流支持
@EnableCaching
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
