package com.mall.business.mapper.sys;

import com.mall.dto.condition.sys.DictDetailConditionDTO;
import com.mall.entity.sys.DictDetailDO;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
public interface DictDetailMapper extends CommonMapper<DictDetailDO, DictDetailConditionDTO> {
    DictDetailDO findById(Long id);
}
