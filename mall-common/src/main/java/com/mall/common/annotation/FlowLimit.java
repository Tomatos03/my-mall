package com.mall.common.annotation;

import com.mall.common.enums.FlowLimitTypeEnum;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解, 控制接口的访问速率
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowLimit {
    /**
     * 资源的key,唯一
     * 作用：不同的接口，不同的流量控制
     */
    String key() default "";

    /**
     * 最多的访问限制次数
     */
    long permitsPerSecond();

    /**
     * 获取令牌最大等待时间
     */
    long timeOut();

    /**
     * 获取令牌最大等待时间,单位(例:分钟/秒/毫秒) 默认:毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    FlowLimitTypeEnum limitType() default FlowLimitTypeEnum.ALL;
}
