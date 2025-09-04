package com.mall.business.mapper;

import com.mall.dto.condition.RoleConditionDTO;
import com.mall.entity.RoleDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
public interface RoleMapper extends CommonMapper<RoleDO, RoleConditionDTO> {
    List<RoleDO> findRoleByUserId(Long userId);

    RoleDO findById(Long id);
}
