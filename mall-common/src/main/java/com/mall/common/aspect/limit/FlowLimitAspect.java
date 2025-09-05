package com.mall.common.aspect.limit;

import com.google.common.collect.Lists;
import com.mall.common.annotation.FlowLimit;
import com.mall.common.enums.FlowLimitResultEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.AuthenticatorUtil;
import com.mall.common.util.IpUtil;
import com.mall.constant.UserConst;
import com.mall.dto.AuthenticatedUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

import static com.mall.common.enums.FlowLimitResultEnum.LIMITED;
import static com.mall.constant.FlowLimitConst.LIMIT_ERROR_MESSAGE;
import static com.mall.constant.FlowLimitConst.LIMIT_KEY_PREFIX;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Aspect
@Slf4j
public class FlowLimitAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DefaultRedisScript<Long> redisScript;

    @Around("@annotation(com.mall.common.annotation.FlowLimit)")
    public Object limit(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        FlowLimit flowLimit = method.getAnnotation(FlowLimit.class);

        String limitKey = buildLimitKey(flowLimit, method.getName());
        long count = flowLimit.permitsPerSecond();
        long time = flowLimit.timeOut();
        Long code = stringRedisTemplate.execute(
                redisScript,
                Lists.newArrayList(limitKey),
                Long.toString(count),
                Long.toString(time)
        );
        if (LIMITED == FlowLimitResultEnum.fromCode(code))
            throw new BusinessException(LIMIT_ERROR_MESSAGE);
        return point.proceed();
    }

    private String buildLimitKey(FlowLimit flowLimit, String methodName) {
        return switch (flowLimit.limitType()) {
            case IP -> {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                String ip = IpUtil.getIpAddr(request);
                yield String.format("%s:%s:%s_%s", LIMIT_KEY_PREFIX, "ip", methodName, ip);
            }
            case USER_ID -> {
                AuthenticatedUserDTO authenticatedUser = AuthenticatorUtil.getAuthenticatedUser();
                if (authenticatedUser == null)
                    throw new BusinessException(UserConst.NOT_LONGIN);
                yield String.format("%s:%s:%s_%s", LIMIT_KEY_PREFIX, "user", methodName,
                                    authenticatedUser.getId());
            }
            default -> String.format("%s:%s:%s", LIMIT_KEY_PREFIX, "all", methodName);
        };
    }
}
