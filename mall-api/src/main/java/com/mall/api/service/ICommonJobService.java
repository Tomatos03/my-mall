package com.mall.api.service;

import com.mall.api.mapper.IAutoFill;
import com.mall.dto.CommonJobDTO;
import com.mall.dto.condition.CommonJobConditionDTO;
import com.mall.entity.CommonJobDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
public interface ICommonJobService extends IAutoFill<CommonJobDTO> {
    int insert(CommonJobDTO dto);

    List<CommonJobDO> searchByCondition(CommonJobConditionDTO condition);
}
