package com.mall.common.annotation.enable;

import com.mall.common.config.FlowLimitConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FlowLimitConfig.class)
public @interface EnableFlowLimit {
}
