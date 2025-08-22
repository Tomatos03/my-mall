package com.mall.aspect;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.mall.domain.cache.RequestCacher;
import com.mall.exception.BusinessException;
import com.mall.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/20
 */
@Aspect
@Component
public class NoRepeatSubmitAspect {
    @Autowired
    RequestCacher requestCacher;

    @Before("@annotation(com.mall.annotation.NoRepeatSubmit)")
    public void beforeAdvice() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String requestUrl = request.getRequestURI();
        String clientIp = IpUtil.getIpAddr(request);
        Map<String, String[]> parameterMap = request.getParameterMap();
        String parameterJson = JSONUtil.toJsonStr(parameterMap);

        String requestInfo = String.format("%s%s%s", requestUrl, clientIp, parameterJson);
        String requestMD5 = DigestUtil.md5Hex(requestInfo);

        boolean isSuccess = requestCacher.save(requestMD5);
        if (!isSuccess)
            throw new BusinessException("请勿重复操作");
    }
}
