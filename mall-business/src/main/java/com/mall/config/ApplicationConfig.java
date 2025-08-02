package com.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@MapperScan(basePackages = "com.mall.mapper")
@Configuration
public class ApplicationConfig {
}
