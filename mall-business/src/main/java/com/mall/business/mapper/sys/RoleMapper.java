package com.mall.business.mapper.sys;

import com.mall.dto.condition.sys.RoleConditionDTO;
import com.mall.entity.sys.RoleDO;

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
