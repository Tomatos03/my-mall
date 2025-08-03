package com.mall.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Component
@Getter
public class JwtProperties {
    @Value("${my_mall.jwt.secret}")
    private String secret;
    @Value("${my_mall.jwt.expiration}")
    private Long expiration;
}
