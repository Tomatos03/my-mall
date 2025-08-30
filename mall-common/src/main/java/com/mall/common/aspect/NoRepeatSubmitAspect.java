package com.mall.common.aspect;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.mall.common.domain.cache.RequestCacher;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
    @Before("@annotation(com.mall.common.annotation.NoRepeatSubmit)")
    public void beforeAdvice() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String requestUrl = request.getRequestURI();
        String clientIp = IpUtil.getIpAddr(request);
        Map<String, String[]> parameterMap = request.getParameterMap();
        String parameterJson = JSONUtil.toJsonStr(parameterMap);

        String requestInfo = String.format("%s%s%s", requestUrl, clientIp, parameterJson);
        String requestMD5 = DigestUtil.md5Hex(requestInfo);

        boolean isSuccess = RequestCacher.save(requestMD5);
        if (!isSuccess)
            throw new BusinessException("请勿重复操作");
    }
}
