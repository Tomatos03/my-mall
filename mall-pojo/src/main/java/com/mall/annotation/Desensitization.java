package com.mall.annotation;


import com.mall.enums.DesensitizationTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitization {
    /**
     * 脱敏类型
     *
     * @return 脱敏类型
     */
    DesensitizationTypeEnum value();
}
