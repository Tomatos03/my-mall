package com.mall.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/8
 */
@Data
@Component
@ConfigurationProperties("my-mall.elasticsearch")
public class EsProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private Map<String, String> index;
}