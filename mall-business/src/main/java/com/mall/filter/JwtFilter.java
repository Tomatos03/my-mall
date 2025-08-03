package com.mall.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mall.constant.RedisKeyConst;
import com.mall.entity.auth.AuthUserEntity;
import com.mall.util.JwtUtil;
import com.mall.util.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromAuthorization(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        boolean validToken = true;
        try {
            Claims payload = jwtUtil.parseVerifyJws(token)
                                    .getPayload();
            username = (String) payload.get("username");
        } catch (RuntimeException e) {
            validToken = false;
        }

        if (!validToken || username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String userJson = getUserJsonByUsername(username);
        AuthUserEntity user = JSONUtil.toBean(userJson, AuthUserEntity.class);

        // 给予权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        // 添加已授权用户
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private String getUserJsonByUsername(String username) {
        return redisUtil.get(RedisKeyConst.USER + username);
    }

    private static final String AUTHORIZATION_PREFIX = "Basic";
    private static final String AUTHORIZATION_SEPARATE = "@";

    private String getTokenFromAuthorization(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StrUtil.isEmpty(token)
                || !token.contains(AUTHORIZATION_PREFIX)
                || !token.contains(AUTHORIZATION_SEPARATE)) {
            return null;
        }

        // Bear@secret
        String[] values = token.split(AUTHORIZATION_SEPARATE);
        if (values.length != 2)
            return null;

        return values[1];
    }
}
