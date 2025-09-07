package com.mall.business.mapper.sys;

import com.mall.dto.condition.sys.DictConditionDTO;
import com.mall.entity.sys.DictDO;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-03
 */
public interface DictMapper extends CommonMapper<DictDO, DictConditionDTO> {
    DictDO findById(Long id);
}
