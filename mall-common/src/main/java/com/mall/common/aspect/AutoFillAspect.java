package com.mall.common.aspect;

import com.mall.common.annotation.AutoFill;
import com.mall.common.util.AuthenticatorUtil;
import com.mall.common.enums.FillTypeEnum;
import com.mall.dto.sys.AuthenticatedUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.mall.common.enums.FillTypeEnum.*;
import static com.mall.constant.AutoFillConst.*;

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
    @Before("execution(* com.mall.api.mapper.IAutoFill.*(..)) " +
            "&& @annotation(com.mall.common.annotation.AutoFill)")
    public void beforeAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        log.info("自动填充公共字段");
        Object[] args = joinPoint.getArgs();

        Object dto = args[0];
        Class<?> clazz = dto.getClass();
        FillTypeEnum type = method.getAnnotation(AutoFill.class).value();

        try {
            AuthenticatedUserDTO authenticatedUser = AuthenticatorUtil.getAuthenticatedUser();
            LocalDateTime now = LocalDateTime.now();

            // 填充创建更新
            Method setUpdateTimeMethod = clazz.getMethod(SET_UPDATE_TIME, LocalDateTime.class);
            setUpdateTimeMethod.invoke(dto, now);

            Long id = authenticatedUser.getId();
            String username = authenticatedUser.getUsername();

            // 填充更新者ID
            Method setUpdateUserIdMethod = clazz.getMethod(SET_UPDATE_USER_ID, Long.class);
            setUpdateUserIdMethod.invoke(dto, id);

            // 填充更新者名称
            Method setUpateUserNameMethod = clazz.getMethod(SET_UPDATE_USER_NAME, String.class);
            setUpateUserNameMethod.invoke(dto, username);

            if (type == INSERT) {
                // 填充创建时间
                Method setCreateTimeMethod = clazz.getMethod(SET_CREATE_TIME, LocalDateTime.class);
                setCreateTimeMethod.invoke(dto, now);

                // 填充创建者ID
                Method setCreateUserIdMethod = clazz.getMethod(SET_CREATE_USER_ID, Long.class);
                setCreateUserIdMethod.invoke(dto, id);

                // 填充创建名称
                Method setCreteUserNameMethod = clazz.getMethod(SET_CREATE_USER_NAME, String.class);
                setCreteUserNameMethod.invoke(dto, username);
            }
        } catch (Exception e) {
            log.error("自动填充字段失败: {}", e.getMessage());
        }
    }
}
