package com.mall.api.service.sys;


import com.mall.dto.sys.DictDetailDTO;
import com.mall.dto.condition.sys.DictDetailConditionDTO;
import com.mall.entity.sys.DictDetailDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
public interface IDictDetailService
        extends ICommonService <DictDetailDO, DictDetailDTO, DictDetailConditionDTO> {
    DictDetailDO findById(Long id);

    List<DictDetailDO> searchDictDetailFromCache(String dictName);
}
