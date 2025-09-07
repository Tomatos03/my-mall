package com.mall.api.service.mall;

import com.mall.dto.mall.UnitDTO;
import com.mall.dto.condition.mall.UnitConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface IUnitService {

    /**
     * 新增
     */
    int insert(UnitDTO dto);

    /**
     * 修改
     */
    int update(UnitDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<UnitDTO> searchByCondition(UnitConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);
}
