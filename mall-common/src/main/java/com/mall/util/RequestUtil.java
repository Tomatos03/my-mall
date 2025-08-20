package com.mall.util;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author : Tomatos
 * @date : 2025/8/5
 */
public abstract class RequestUtil {
    private static final String AUTHORIZATION_PREFIX = "Basic";
    private static final String AUTHORIZATION_SEPARATE = "@";


    /**
     * 从 request 请求的header中获取token
     *
     * @param request request请求
     * @return java.lang.String token
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/5 16:01
     */
    public static String getToken(HttpServletRequest request) {
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
