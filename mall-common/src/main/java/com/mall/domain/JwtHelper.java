package com.mall.domain;

import com.mall.properties.JwtProperties;
import com.mall.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/6
 */
@Component
public class JwtHelper {
    @Autowired
    private JwtProperties jwtProperties;

    public String createUserToken(String username) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        return JwtUtil.createJwt(payload, jwtProperties);
    }

    public String getUsernameFromToken(String token) {
        Claims payload;
        try {
            payload = JwtUtil.parseVerifyJws(token, jwtProperties)
                             .getPayload();
        } catch (RuntimeException runtimeException) {
            return null;
        }
        return payload.get("username", String.class);
    }
}
