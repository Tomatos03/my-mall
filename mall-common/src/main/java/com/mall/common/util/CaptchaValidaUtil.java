package com.mall.common.util;

import com.mall.common.exception.CaptchaExpiredException;
import com.mall.common.exception.InvalidCaptchaException;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/5
 */
public final class CaptchaValidaUtil {
    private CaptchaValidaUtil() {};

    public static void validate(String actualCode, String code) {
        if (actualCode == null)
            throw new CaptchaExpiredException();

        if (!actualCode.equalsIgnoreCase(code))
            throw new InvalidCaptchaException();
    }
}
