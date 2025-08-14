package com.mall.mapper;

import com.mall.annotation.AutoFill;
import com.mall.constant.FillType;
import com.mall.entity.MenuDO;
import com.mall.entity.condition.MenuConditionDTO;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
public interface MenuMapper extends BaseMapper<MenuDO, MenuConditionDTO> {
    List<MenuDO> findMenuByRoleIdList(Collection<Long> roleIdList);

    int batchDelete(List<Long> ids);

    @AutoFill(FillType.INSERT)
    int insert(MenuDO menuDO);

    @AutoFill(FillType.UPDATE)
    int update(MenuDO menuDO);

    @Override
    List<MenuDO> searchByCondition(MenuConditionDTO menuConditionDTO) throws DataAccessException;
}
