package com.mall.business.mapper.sys;

import com.mall.entity.sys.CommonTaskDO;
import com.mall.dto.condition.sys.CommonTaskConditionDTO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
public interface CommonTaskMapper extends CommonMapper<CommonTaskDO, CommonTaskConditionDTO> {
    List<CommonTaskDO> searchByCondition(CommonTaskConditionDTO commonTaskCondition);

//    void insert(CommonTaskDO commonTaskDO);
//
//    void update(CommonTaskDO commonTaskDO);
}