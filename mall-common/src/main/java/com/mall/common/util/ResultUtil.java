package com.mall.common.util;

import com.mall.model.Result;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
public final class ResultUtil {
    private ResultUtil() {};

    /**
     * 请求成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 接口相应实体
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(Result.OK, null, data);
    }

    /**
     * 请求成功
     *
     * @param <T> 数据类型
     * @return 接口相应实体
     */
    public static <T> Result<T> success() {
        return success(null);
    }

        /**
     * 请求成功
     *
     * @param code    返回码
     * @param message 返回信息
     * @param <T>     数据类型
     * @return 接口相应实体
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message, null);
    }
}
