package com.mall.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
@Component
@ConfigurationProperties(prefix = "oss.qiniu")
@Data
public class QiNiuConfig {

    /**
     * AccessKey
     */
    private String accessKey;
    /**
     * SecretKey
     */
    private String secretKey;
    /**
     * 图片存储空间名
     */
    private String pictureBucketName;
    /**
     * 图片外链
     */
    private String pictureDomain;
    /**
     * 文件存储空间名
     */
    private String fileBucketName;
    /**
     * 文件外链
     */
    private String fileDomain;
}
