package com.mall.util;

import com.mall.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
public abstract class JwtUtil {
    public static String createJwt(Map<String, Object> payload, JwtProperties jwtProperties) {
        // expiration以秒为单位, 这里需要转毫秒
        return Jwts.builder()
                   .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
                   .claims(payload)
                   .compact();
    }

    public static Jws<Claims> parseVerifyJws(String jws, JwtProperties jwtProperties) throws JwtException {
        return Jwts.parser()
                   .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
                   .build() // 构建一个使用secretKey验证JWS的Parser对象
                   .parseSignedClaims(jws); // 解析得到 payload
    }
}
