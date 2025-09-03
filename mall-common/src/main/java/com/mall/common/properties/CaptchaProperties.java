package com.mall.common.properties;

import com.mall.api.properties.CaptchaProps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/5
 */
@Setter
@Component
@ConfigurationProperties("my-mall.captcha")
public class CaptchaProperties implements CaptchaProps {
    private Long expiration;

    @Override
    public Long expiration() {
        return expiration;
    }
}
