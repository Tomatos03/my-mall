package com.mall.common.aspect;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.mall.api.service.ILogService;
import com.mall.common.annotation.AddLog;
import com.mall.entity.LogDO;
import com.mall.common.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
@Slf4j
@Aspect
@Component
public class AddLogAspect {
    private static final int COMPLETED = 1;
    private static final int NO_COMPLETED = 0;
    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.mall.common.annotation.AddLog)")
    public void pointCut() {};

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long consumeTime = System.currentTimeMillis() - startTime;

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = point.getArgs();

        LogDO logDO = LogDO.builder()
                           .description(getDescription(method))
                           .methodName(getMethodName(method))
                           .status(COMPLETED)
                           .requestIp(IpUtil.getIpAddr(request))
                           .url(request.getRequestURI())
                           .browser(getBrowserName(request))
                           .param(buildParamJson(parameterNames, parameterValues))
                           .time((int) consumeTime)
                           .createUserId(9L)
                           .createUserName("admin")
                           .build();
        logService.save(logDO);
        return result;
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint point, Throwable ex) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = point.getArgs();

        LogDO logDO = LogDO.builder()
                           .description(getDescription(method))
                           .methodName(getMethodName(method))
                           .status(NO_COMPLETED)
                           .requestIp(IpUtil.getIpAddr(request))
                           .url(request.getRequestURI())
                           .browser(getBrowserName(request))
                           .time(-1)
                           .param(buildParamJson(parameterNames, parameterValues))
                           .exception(ex.getMessage())
                           .createUserId(9L)
                           .createUserName("admin")
                           .build();

        logService.save(logDO);
    }

    private String buildParamJson(String[] parameterNames, Object[] parameterValues) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");

        int size = parameterNames.length;
        for (int i = 0; i < size; ++i) {
            stringBuilder.append(parameterNames[i])
                         .append(": ")
                         .append(parameterValues[i]);

            if (i != size - 1)
                stringBuilder.append(",");
        }

        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    private String getBrowserName(HttpServletRequest httpServletRequest) {
        String userAgentStr = httpServletRequest.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        return userAgent.getBrowser().toString();
    }

    private String getDescription(Method method) {
        AddLog annotation = method.getAnnotation(AddLog.class);
        return annotation.value();
    }

    public String getMethodName(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }
}
