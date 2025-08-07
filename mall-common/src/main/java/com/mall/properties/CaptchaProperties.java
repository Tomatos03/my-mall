package com.mall.properties;

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
public class CaptchaProperties {
    @Value("${my_mall.captcha.expiration}")
    private Long expiration;
}
