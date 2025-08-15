package com.mall.aspect;

import com.mall.annotation.AutoFill;
import com.mall.constant.AutoFillConst;
import com.mall.enums.FillType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
@Slf4j
@Aspect
@Component
public class AutoFillAspect {
    // @annotation(com.takeout.annotation.AutoFill) 限定只拦截有该注解的方法
    @Before("execution(* com.mall.mapper.*.*(..)) && @annotation(com.mall.annotation.AutoFill)")
    public void beforeAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        log.info("自动填充公共字段");
        Object[] args = joinPoint.getArgs();

        Object dto = args[0];
        Class<?> clazz = dto.getClass();
        FillType type = method.getAnnotation(AutoFill.class).value();

        try {
            LocalDateTime now = LocalDateTime.now();

            Method setUpdateTimeMethod = clazz.getMethod(AutoFillConst.SET_UPDATE_TIME, LocalDateTime.class);
            setUpdateTimeMethod.invoke(dto, now);

            if (type == FillType.INSERT) {
                Method setCreateTimeMethod = clazz.getMethod(AutoFillConst.SET_CREATE_TIME, LocalDateTime.class);
                setCreateTimeMethod.invoke(dto, now);
            }
        } catch (Exception e) {
            log.error("自动填充字段失败: {}", e.getMessage());
        }
    }
}
