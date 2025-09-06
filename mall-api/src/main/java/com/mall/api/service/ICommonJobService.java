package com.mall.api.service;

import com.mall.dto.condition.CommonJobConditionDTO;
import com.mall.entity.CommonJobDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
public interface ICommonJobService {
    int insert(CommonJobDO jobDO);

    List<CommonJobDO> searchByCondition(CommonJobConditionDTO condition);
}
