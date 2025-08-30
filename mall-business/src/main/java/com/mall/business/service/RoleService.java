package com.mall.business.service;

import com.mall.api.service.IRoleService;
import com.mall.business.mapper.RoleMapper;
import com.mall.entity.RoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDO> findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
