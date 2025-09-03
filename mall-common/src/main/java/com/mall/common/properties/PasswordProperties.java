package com.mall.common.properties;

import com.mall.api.properties.IPassWordProps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@Component
@Setter
@Slf4j
@ConfigurationProperties("my-mall.password")
public class PasswordProperties implements IPassWordProps {
    private String privateKeyPath;

    @Override
    public String privateKey() {
        String key = "";
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(privateKeyPath);){
            key = new String(Objects.requireNonNull(in)
                                    .readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("私钥读取失败, 原因: {}", e.getMessage());
        }
        return key;
    }
}
