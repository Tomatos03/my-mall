package com.mall.business.service.sys;

import com.mall.api.service.sys.IUserRoleService;
import com.mall.business.mapper.sys.CommonMapper;
import com.mall.business.mapper.sys.UserRoleMapper;
import com.mall.dto.sys.UserRoleDTO;
import com.mall.dto.condition.sys.UserRoleConditionDTO;
import com.mall.entity.sys.UserRoleDO;
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
