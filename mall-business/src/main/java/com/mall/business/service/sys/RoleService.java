package com.mall.business.service.sys;

import com.mall.api.service.sys.IRoleService;
import com.mall.business.mapper.sys.CommonMapper;
import com.mall.business.mapper.sys.RoleMapper;
import com.mall.dto.sys.RoleDTO;
import com.mall.dto.condition.sys.PageConditionDTO;
import com.mall.dto.condition.sys.RoleConditionDTO;
import com.mall.entity.sys.RoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
@Service
public class RoleService extends CommonService<RoleDO, RoleDTO, RoleConditionDTO>
        implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    protected RoleService() {
        super(RoleDO.class);
    }

    @Override
    public List<RoleDO> findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public RoleDO findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public List<RoleDO> queryAllRole() {
        RoleConditionDTO condition = new RoleConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);

        return roleMapper.searchByCondition(condition);
    }

    @Override
    protected CommonMapper<RoleDO, RoleConditionDTO> getMapper() {
        return this.roleMapper;
    }
}
