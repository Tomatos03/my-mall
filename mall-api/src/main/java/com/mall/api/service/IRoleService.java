package com.mall.api.service;

import com.mall.entity.RoleDO;

import java.util.Collection;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IRoleService {
    List<RoleDO> findRoleByUserId(Long userId);
}
