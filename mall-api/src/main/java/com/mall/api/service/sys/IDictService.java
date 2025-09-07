package com.mall.api.service.sys;

import com.mall.dto.sys.DictDTO;
import com.mall.dto.condition.sys.DictConditionDTO;
import com.mall.entity.sys.DictDO;


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

