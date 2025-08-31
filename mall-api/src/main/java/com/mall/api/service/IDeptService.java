package com.mall.api.service;

import com.mall.dto.DeptDTO;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.entity.DeptDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface IDeptService extends ICommonService<DeptDO, DeptDTO, DeptConditionDTO> {
    List<DeptDTO> searchByTree(DeptConditionDTO deptCondition);
}