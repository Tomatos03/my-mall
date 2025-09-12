package com.mall.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/12
 */
@Data
@Component
@ConfigurationProperties("my-mall.security.mock-user")
public class MockUserConfig {
    private boolean enabled = false;
}
