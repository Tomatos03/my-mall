package com.mall.api.service;

import com.mall.dto.RoleDTO;
import com.mall.dto.condition.RoleConditionDTO;
import com.mall.entity.RoleDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IRoleService extends ICommonService<RoleDO, RoleDTO, RoleConditionDTO>{
    List<RoleDO> findRoleByUserId(Long userId);

    RoleDO findById(Long id);

    List<RoleDO> queryAllRole();
}
