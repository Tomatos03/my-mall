package com.mall.api.properties;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface JwtProps {
    String secret();
    Long expiration();
}
