package com.mall.business.mapper;

import com.mall.entity.UserRoleDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
public interface UserRoleMapper {
    void insert(UserRoleDO userRoleDO);
    void batchInsert(List<UserRoleDO> userRoleDOS);
}
