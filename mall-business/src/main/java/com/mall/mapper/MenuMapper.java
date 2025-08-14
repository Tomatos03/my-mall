package com.mall.mapper;

import com.mall.entity.MenuDO;
import com.mall.entity.condition.MenuConditionDTO;

import java.util.Collection;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
public interface MenuMapper extends BaseMapper<MenuDO, MenuConditionDTO> {
    List<MenuDO> findMenuByRoleIdList(Collection<Long> roleIdList);
    int batchDelete(List<Long> ids);
}
