package com.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Data
@AllArgsConstructor
public class Result<T> {
    /**
     * 请求成功状态码
     */
    public static final int OK = HttpStatus.OK.value();

    /**
     * 接口返回码
     */
    private int code;

    /**
     * 接口返回信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;
}
