package com.mall.business.mapper;

import com.mall.api.mapper.AutoFillMapper;
import com.mall.common.annotation.AutoFill;
import com.mall.common.enums.FillTypeEnum;
import com.mall.entity.MenuDO;
import com.mall.dto.condition.MenuConditionDTO;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
public interface MenuMapper extends BaseMapper<MenuDO, MenuConditionDTO>, AutoFillMapper<MenuDO> {
    List<MenuDO> findMenuByRoleIdList(Collection<Long> roleIdList);

    int batchDelete(List<Long> ids);

    @AutoFill(FillTypeEnum.INSERT)
    int insert(MenuDO menuDO);

    @AutoFill(FillTypeEnum.UPDATE)
    int update(MenuDO menuDO);

    @Override
    List<MenuDO> searchByCondition(MenuConditionDTO menuConditionDTO) throws DataAccessException;

    @Override
    int searchCount(MenuConditionDTO menuConditionDTO);
}
