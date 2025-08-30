package com.mall.common.util;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/23
 */
public final class Ip2regionUtil {
    private static final String DB_PATH = "ip2region/ip2region.xdb";
    private static final Searcher SEARCHER = initSearcher();

    private Ip2regionUtil() {}

    private static Searcher initSearcher() {
        try (InputStream in = new ClassPathResource(DB_PATH).getInputStream()) {
            byte[] bytes = in.readAllBytes();
            return Searcher.newWithBuffer(bytes);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("无法加载 ip2region 数据库: " + e.getMessage());
        }
    }

    /**
     * 查询 IP，返回原始字符串数组
     * 例：{"中国","0","广西","崇左市","移动"}
     */
    public static String[] queryRaw(String ip) {
        Objects.requireNonNull(ip, "ip 不能为空");
        try {
            return SEARCHER.search(ip).split("\\|");
        } catch (Exception e) {
            throw new RuntimeException("ip2region 查询失败: " + e.getMessage(), e);
        }
    }

    public static String country(String ip) {
        return queryRaw(ip)[0];
    }

    public static String province(String ip) {
        return queryRaw(ip)[2];
    }

    public static String city(String ip) {
        return queryRaw(ip)[3];
    }

    public static String isp(String ip) {
        return queryRaw(ip)[4];
    }
}
