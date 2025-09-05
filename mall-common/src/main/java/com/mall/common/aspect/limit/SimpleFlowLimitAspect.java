package com.mall.common.aspect.limit;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mall.common.annotation.FlowLimit;
import com.mall.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;

import static com.mall.constant.FlowLimitConst.LIMIT_ERROR_MESSAGE;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Aspect
@Slf4j
public class SimpleFlowLimitAspect {
    private Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Around("@annotation(com.mall.common.annotation.FlowLimit)")
    public Object limit(ProceedingJoinPoint point) throws Throwable {
        FlowLimit flowLimit = getFlowLimitAnnotationFromJoinPoint(point);
        if (flowLimit == null)
            return null;

        String key = flowLimit.key();
        long speed = flowLimit.permitsPerSecond();
        RateLimiter rateLimiter = limiterMap.computeIfAbsent(key, (oldKey) -> {
            log.info("创建令牌桶:{}, 每秒速率:{}", key, speed);
            return RateLimiter.create(speed);
        });

        boolean isTrySuccess = rateLimiter.tryAcquire();
        if (!isTrySuccess)
            throw new BusinessException(LIMIT_ERROR_MESSAGE);

        return point.proceed();
    }

    private static FlowLimit getFlowLimitAnnotationFromJoinPoint(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(FlowLimit.class);
    }
}
