package com.mall.business.mapper.sys;

import com.mall.dto.condition.sys.UserRoleConditionDTO;
import com.mall.entity.sys.UserRoleDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
public interface UserRoleMapper extends CommonMapper<UserRoleDO, UserRoleConditionDTO> {
    void batchInsert(List<UserRoleDO> userRoleList);

    void deleteByUserId(Long userId);
}
