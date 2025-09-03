package com.mall.business.mapper;

import com.mall.dto.condition.DictConditionDTO;
import com.mall.entity.DictDO;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-03
 */
public interface DictMapper extends CommonMapper<DictDO, DictConditionDTO> {
    DictDO findById(Long id);
}
