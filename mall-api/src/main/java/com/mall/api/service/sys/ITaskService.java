package com.mall.api.service.sys;

import com.mall.entity.sys.CommonTaskDO;
import com.mall.dto.condition.sys.CommonTaskConditionDTO;

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
