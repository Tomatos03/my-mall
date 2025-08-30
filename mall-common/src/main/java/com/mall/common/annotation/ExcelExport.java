package com.mall.common.annotation;

import com.mall.common.enums.ExcelBizTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExport {
    ExcelBizTypeEnum value();
}
