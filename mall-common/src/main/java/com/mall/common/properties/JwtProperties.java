package com.mall.common.properties;

import com.mall.api.properties.JwtProps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Component
@Getter
@Setter
@ConfigurationProperties("my-mall.jwt")
public class JwtProperties implements JwtProps {
    private String secret;
    private Long expiration;

    @Override
    public String secret() {
        return secret;
    }

    @Override
    public Long expiration() {
        return expiration;
    }
}
