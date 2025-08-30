package com.mall.common.domain;

import com.mall.api.properties.JwtProps;
import com.mall.common.util.JwtUtil;
import io.jsonwebtoken.Claims;

import java.util.HashMap;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/6
 */
public final class JwtHelper {
    private JwtHelper() {};

    public static String createUserToken(String username, JwtProps props) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("username", username);

        return JwtUtil.createJwt(payload, props);
    }

    public static String getUsernameFromToken(String token, JwtProps props) {
        Claims payload;
        try {
            payload = JwtUtil.parseVerifyJws(token, props)
                             .getPayload();
        } catch (RuntimeException runtimeException) {
            return null;
        }
        return payload.get("username", String.class);
    }
}
