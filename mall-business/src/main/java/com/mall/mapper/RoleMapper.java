package com.mall.mapper;

import com.mall.entity.RoleDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
public interface RoleMapper {
    List<RoleDO> findRoleByUserId(Long userId);
}
