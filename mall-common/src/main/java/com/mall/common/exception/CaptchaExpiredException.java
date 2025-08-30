package com.mall.common.exception;

/**
 * @author : Tomatos
 * @date : 2025/8/5
 */
public class CaptchaExpiredException extends RuntimeException {
    public CaptchaExpiredException() {
        super("验证码过期");
    }
}
