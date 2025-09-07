package com.mall.business.mapper.sys;

import com.mall.entity.sys.DeptDO;
import com.mall.dto.condition.sys.DeptConditionDTO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public interface DeptMapper extends CommonMapper<DeptDO, DeptConditionDTO> {
    List<DeptDO> searchByCondition(DeptConditionDTO deptCondition);

    DeptDO searchById(Long id);

    @Override
    int searchCount(DeptConditionDTO deptCondition);
}