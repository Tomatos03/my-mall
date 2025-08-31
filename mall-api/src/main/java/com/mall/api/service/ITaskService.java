package com.mall.api.service;

import com.mall.entity.CommonTaskDO;
import com.mall.dto.condition.CommonTaskConditionDTO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface ITaskService {
    void insert(CommonTaskDO commonTaskDO);

    void update(CommonTaskDO commonTaskDO);

    List<CommonTaskDO> searchByCondition(CommonTaskConditionDTO commonTaskCondition);
}
