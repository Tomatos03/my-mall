package com.mall.common.properties;

import com.mall.api.properties.CaptchaProps;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/5
 */
@Getter
@Component
public class CaptchaProperties implements CaptchaProps {
    @Value("${my_mall.captcha.expiration}")
    private Long expiration;

    @Override
    public Long expiration() {
        return expiration;
    }
}
