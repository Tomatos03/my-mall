package com.mall.domain;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.mall.util.HttpUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Component
@ConfigurationProperties(prefix = "ip-geolocation")
@Data
public class IpApiHelper {
    private String api;
    private String cityFieldName;
    private String countryFieldName;
    private String provinceFieldName;
    private Map<String, String> params;

    public Map<String, String> queryInfo(String ipStr) throws IOException, InterruptedException {
        api += ipStr;
        String responseBody = HttpUtil.doGet(api, params).body();
        return JSONUtil.toBean(responseBody, new TypeReference<>(){}, false);
    }
}