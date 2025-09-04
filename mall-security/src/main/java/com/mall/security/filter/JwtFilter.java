package com.mall.security.filter;

import cn.hutool.json.JSONUtil;
import com.mall.api.properties.JwtProps;
import com.mall.common.domain.JwtHelper;
import com.mall.common.domain.cache.TokenCacher;
import com.mall.common.domain.cache.UserCacher;
import com.mall.dto.AuthenticatedUserDTO;
import com.mall.common.util.RequestUtil;
import com.mall.common.util.AuthenticatorUtil;
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
    private JwtProps jwtProps;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("请求地址: {}", request.getRequestURI());

        String token, username, cacheToken;
        if ((token = RequestUtil.getToken(request)) != null
        && (username = JwtHelper.getUsernameFromToken(token, jwtProps)) != null
        && (cacheToken = TokenCacher.getTokenFromCache(username)) != null
        && (token.equals(cacheToken))
        ) {

            String userJson = UserCacher.getUserJson(username);
            AuthenticatedUserDTO authenticatedUserDTO = JSONUtil.toBean(userJson, AuthenticatedUserDTO.class);
            AuthenticatorUtil.restoreAuthentication(authenticatedUserDTO);
        }
        log.info("请求认证通过");
        filterChain.doFilter(request, response);
    }
}
