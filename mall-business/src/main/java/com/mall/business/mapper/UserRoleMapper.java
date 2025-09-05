package com.mall.business.mapper;

import com.mall.dto.condition.UserRoleConditionDTO;
import com.mall.entity.UserRoleDO;

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
