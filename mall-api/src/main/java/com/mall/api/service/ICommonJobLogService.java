package com.mall.api.service;

import com.mall.api.mapper.IAutoFill;
import com.mall.dto.CommonJobLogDTO;
import com.mall.dto.condition.CommonJobLogConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-05
 */
public interface ICommonJobLogService extends IAutoFill<CommonJobLogDTO> {

    /**
     * 新增
     */
    int insert(CommonJobLogDTO dto);

    /**
     * 修改
     */
    int update(CommonJobLogDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<CommonJobLogDTO> searchByCondition(CommonJobLogConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);
}
