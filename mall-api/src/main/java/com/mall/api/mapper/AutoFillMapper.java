package com.mall.api.mapper;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface AutoFillMapper<T> {
    int insert(T entity);
    int update(T entity);
}
