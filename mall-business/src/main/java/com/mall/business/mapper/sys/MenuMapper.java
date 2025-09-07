package com.mall.business.mapper.sys;

import com.mall.entity.sys.MenuDO;
import com.mall.dto.condition.sys.MenuConditionDTO;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
public interface MenuMapper extends CommonMapper<MenuDO, MenuConditionDTO> {
    List<MenuDO> findMenuByRoleIdList(Collection<Long> roleIdList);

    int deleteByIds(List<Long> ids);

    int insert(MenuDO menuDO);

    int update(MenuDO menuDO);

    @Override
    List<MenuDO> searchByCondition(MenuConditionDTO menuCondition) throws DataAccessException;

    @Override
    int searchCount(MenuConditionDTO menuCondition);
}