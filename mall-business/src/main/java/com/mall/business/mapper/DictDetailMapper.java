package com.mall.business.mapper;

import com.mall.dto.condition.DictDetailConditionDTO;
import com.mall.entity.DictDetailDO;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
public interface DictDetailMapper extends CommonMapper<DictDetailDO, DictDetailConditionDTO> {
    DictDetailDO findById(Long id);
}
