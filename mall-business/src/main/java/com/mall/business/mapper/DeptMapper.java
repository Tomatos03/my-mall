package com.mall.business.mapper;

import com.mall.api.mapper.AutoFillMapper;
import com.mall.entity.DeptDO;
import com.mall.dto.condition.DeptConditionDTO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public interface DeptMapper extends BaseMapper<DeptDO, DeptConditionDTO>, AutoFillMapper<DeptDO> {
    List<DeptDO> searchByCondition(DeptConditionDTO deptConditionDTO);

    DeptDO searchById(Long id);

    /**
     * 添加部门
     *
     * @param deptDO 部门信息
     * @return 结果
     */
    int insert(DeptDO deptDO);

    /**
     * 修改部门
     *
     * @param deptDO 部门信息
     * @return 结果
     */
    int update(DeptDO deptDO);

    /**
     * 删除部门
     *
     * @param ids    id集合
     * @return 结果
     */
    int deleteByIds(List<Long> ids);

    @Override
    int searchCount(DeptConditionDTO deptConditionDTO);
}
