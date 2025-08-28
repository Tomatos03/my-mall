package com.mall.domain;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/23
 */
@Component
public class Ip2regionSearcher {
    @Value("${ip2region.db-path}")
    private String dbPath;
    private Searcher searcher;

    /**
     * 查询Ip地址的城市,省份相关信息
     *
     * @param ipStr ip地址字符串
     * @return java.lang.String[] 中国|0|广西|崇左市|移动
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 10:53
     */
    public String[] queryIpInfo(String ipStr) {
        ensureSearcherInitialized();

        String[] result;
        try {
            result = searcher.search(ipStr).split("\\|");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 获取省份信息
     *
     * @param ipInfo queryIpInfo方法获取的ip信息字符串数组
     * @return java.lang.String
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 10:56
     */
    public String getProvince(String[] ipInfo) {
        return ipInfo[2];
    }

    /**
     * 获取城市信息
     *
     * @param ipInfo queryIpInfo方法获取的ip信息字符串数组
     * @return java.lang.String
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 10:56
     */
    public String getCity(String[] ipInfo) {
        return ipInfo[3];
    }

    /**
     * 获取国家信息
     *
     * @param ipInfo queryIpInfo方法获取的ip信息字符串数组
     * @return java.lang.String
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 10:56
     */
    public String getCountry(String[] ipInfo) {
        return ipInfo[0];
    }

    /**
     *  确保Searcher对象已经初始化
     *
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 10:57
     */
    private void ensureSearcherInitialized() {
        if (searcher != null)
            return;

        try {
            byte[] bytes = new ClassPathResource("ip2region/ip2region.xdb")
                    .getInputStream()
                    .readAllBytes();

            searcher = Searcher.newWithBuffer(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
