package com.mall.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/20
 */
public abstract class IpUtil {

    private IpUtil() {
    }

    /**
     * 获取用户IP
     *
     * @param request 请求
     * @return 用户IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null)
            return "unknown";

        String[] headers = {
                "x-forwarded-for",
                "Proxy-Client-IP",
                "X-Forwarded-For",
                "WL-Proxy-Client-IP",
                "X-Real-IP"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // x-forwarded-for 可能有多个IP，取第一个
                if (ip.contains(","))
                    ip = ip.split(",")[0].trim();

                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
