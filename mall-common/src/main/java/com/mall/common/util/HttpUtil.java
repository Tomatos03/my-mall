package com.mall.common.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
public abstract class HttpUtil {
    private HttpUtil() {};

    /**
     * 发送GET请求
     *
     * @param address 请求地址
     * @return java.net.http.HttpResponse<java.lang.String>
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 11:11
     */
    public static HttpResponse<String> doGet(String address) throws IOException, InterruptedException {
        return doGet(address, null);
    }

    public static HttpResponse<String> doGet(String address, Map<String, String> params) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        address += getParamsStr(params);

        HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(address))
                                             .GET()
                                             .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String getParamsStr(Map<String, String> params) {
        if (noParameters(params))
            return "";

        StringBuilder paramStr = new StringBuilder();
        paramStr.append("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            paramStr.append(paramName)
                    .append("=")
                    .append(paramValue);
        }
        return paramStr.toString();
    }

    private static boolean noParameters(Map<String, String> params) {
        return params == null || params.isEmpty();
    }
}
