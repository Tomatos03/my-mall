package com.mall.api.service.sys;

import com.mall.dto.sys.RoleDTO;
import com.mall.dto.condition.sys.RoleConditionDTO;
import com.mall.entity.sys.RoleDO;

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
