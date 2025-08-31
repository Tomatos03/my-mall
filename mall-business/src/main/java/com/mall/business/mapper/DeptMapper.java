package com.mall.business.mapper;

import com.mall.api.mapper.IAutoFill;
import com.mall.entity.DeptDO;
import com.mall.dto.condition.DeptConditionDTO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public interface DeptMapper extends CommonMapper<DeptDO, DeptConditionDTO>, IAutoFill {
    List<DeptDO> searchByCondition(DeptConditionDTO deptCondition);

    DeptDO searchById(Long id);

    @Override
    int searchCount(DeptConditionDTO deptCondition);
}