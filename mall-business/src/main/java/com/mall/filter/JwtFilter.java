package com.mall.filter;

import cn.hutool.json.JSONUtil;
import com.mall.domain.security.Authenticator;
import com.mall.domain.JwtHelper;
import com.mall.domain.cache.TokenCacher;
import com.mall.domain.cache.UserCacher;
import com.mall.dto.AuthenticatedUserDTO;
import com.mall.util.RequestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    TokenCacher tokenCacher;
    @Autowired
    UserCacher userCacher;
    @Autowired
    Authenticator authenticator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("请求地址: {}", request.getRequestURI());

        String token, username;
        if ((token = RequestUtil.getToken(request)) != null
        && (username = jwtHelper.getUsernameFromToken(token)) != null
        && (token.equals(tokenCacher.getTokenFromCache(username)))
        ) {
            String userJson = userCacher.getUserJson(username);
            AuthenticatedUserDTO authenticatedUserDTO = JSONUtil.toBean(userJson, AuthenticatedUserDTO.class);
            authenticator.restoreAuthentication(authenticatedUserDTO);
        }

        filterChain.doFilter(request, response);
    }
}
