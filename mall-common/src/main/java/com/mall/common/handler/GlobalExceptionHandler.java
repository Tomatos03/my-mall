package com.mall.common.handler;

import com.mall.model.Result;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 统一处理异常
     *
     * @param e 异常
     * @return API请求响应实体
     */
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) throws Throwable {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            log.info("请求出现业务异常：", e);
            return ResultUtil.error(businessException.getCode(), businessException.getMessage());
        }
        log.error("请求出现系统异常：", e);
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");
    }
}
