package com.mall.domain;

import com.mall.exception.CaptchaExpiredException;
import com.mall.exception.InvalidCaptchaException;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/5
 */
@Component
public class CaptchaHelper {
    public void validate(String actualCode, String code) {
        if (actualCode == null)
            throw new CaptchaExpiredException();

        if (!actualCode.equalsIgnoreCase(code))
            throw new InvalidCaptchaException();
    }
}
