package com.mall.business.mapper;

import com.mall.entity.CommonTaskDO;
import com.mall.entity.condition.CommonTaskCondition;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
public interface CommonTaskMapper extends BaseMapper<CommonTaskDO, CommonTaskCondition> {
    List<CommonTaskDO> searchByCondition(CommonTaskCondition commonTaskCondition);

    void insert(CommonTaskDO commonTaskDO);

    void update(CommonTaskDO commonTaskDO);
}