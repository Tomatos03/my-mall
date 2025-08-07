package com.mall.exception;

/**
 * @author : Tomatos
 * @date : 2025/8/5
 */
public class InvalidCaptchaException extends RuntimeException{
    public InvalidCaptchaException() {
        super("验证码错误");
    }
}
