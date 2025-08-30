package com.mall.common.properties;

import com.mall.api.properties.JwtProps;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Component
@Getter
public class JwtProperties implements JwtProps {
    @Value("${mall.web.security.privateKey:MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAv5YziQe5zP5OAyVYfIh2fJDiJuEeD4ZGNpVQjiPvsNGhvwjF/SCDECMwZl1lYAL6wNDNNXhr/ILV5xF0ZHg0+wIDAQABAkBDQRjqO5oPyh3coNmFLepaJAofl9sTnBE9zACxRRA5Q2ErwtUVi+A4qLX+HPDbPFWCbvEPKsGods4BiAL+rxWxAiEA+W9p3bfu+JA2XTxEboH9Cd5/g92PZGALne+cVKAKfFcCIQDEoQebpFK/hw8AWvWRZ4vXZLoocv8qEhnPEySCqTtl/QIhAK0vdSLTdaGxh31+vci4ijcS/BhTeh7oLMiwuCttnzorAiEAouivvvokPN8PRMIX10KtD+Y6fizYz+hzTaeUhlTkC5kCIQCPDDmumEPGzUDmFe1hlL3AqUeCNNnVhxYQrI4bNHFZvQ==}")
    private String secret;
    @Value("${my_mall.jwt.expiration}")
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
