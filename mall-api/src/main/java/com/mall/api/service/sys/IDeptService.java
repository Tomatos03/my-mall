package com.mall.api.service.sys;

import com.mall.dto.sys.DeptDTO;
import com.mall.dto.condition.sys.DeptConditionDTO;
import com.mall.entity.sys.DeptDO;

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