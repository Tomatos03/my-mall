package com.mall.business.service;

import com.mall.api.service.IUserRoleService;
import com.mall.business.mapper.CommonMapper;
import com.mall.business.mapper.UserRoleMapper;
import com.mall.dto.UserRoleDTO;
import com.mall.dto.condition.UserRoleConditionDTO;
import com.mall.entity.UserRoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
@Service
public class UserRoleService
        extends CommonService <UserRoleDO, UserRoleDTO, UserRoleConditionDTO>
        implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    protected UserRoleService() {
        super(UserRoleDO.class);
    }

    @Override
    protected CommonMapper<UserRoleDO, UserRoleConditionDTO> getMapper() {
        return this.userRoleMapper;
    }
}
