package com.mall.api.mapper;

/**
 * 标记接口
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface IAutoFill<DO> {
    int insert(DO entity);
    int update(DO entity);
}