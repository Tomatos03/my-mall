package com.mall.common.annotation.sensitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对方法的参数对象进行敏感词校验(前提是参数对象的字段上有@SensitiveWord注解)
 *
 * @author : Tomatos
 * @date : 2025/9/11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveWordCheck {
}
