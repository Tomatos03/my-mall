package com.mall.api.service;

import com.mall.dto.DictDTO;
import com.mall.dto.condition.DictConditionDTO;
import com.mall.entity.DictDO;


/**
 *
 *
 * @author Tomatos
 * @date 2025-09-03
 */
public interface IDictService extends ICommonService <DictDO, DictDTO, DictConditionDTO> {
    DictDTO findById(Long id);

    void refreshDictCache();
}

