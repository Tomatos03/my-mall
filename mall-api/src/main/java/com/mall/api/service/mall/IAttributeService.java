package com.mall.api.service.mall;

import com.mall.dto.mall.AttributeDTO;
import com.mall.dto.condition.mall.AttributeConditionDTO;
import com.mall.dto.sys.PageDTO;
import com.mall.entity.mall.AttributeDO;

import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface IAttributeService {

    /**
     * 新增
     */
    int insert(AttributeDTO dto);

    /**
     * 修改
     */
    int update(AttributeDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<AttributeDTO> searchByCondition(AttributeConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);

    AttributeDTO findById(Long id);

    PageDTO<AttributeDO> searchByPage(AttributeConditionDTO condition);
}
