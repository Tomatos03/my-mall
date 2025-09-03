package com.mall.api.service;


import com.mall.dto.DictDetailDTO;
import com.mall.dto.condition.DictDetailConditionDTO;
import com.mall.entity.DictDetailDO;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
public interface IDictDetailService
        extends ICommonService <DictDetailDO, DictDetailDTO, DictDetailConditionDTO> {
    DictDetailDO findById(Long id);
}
