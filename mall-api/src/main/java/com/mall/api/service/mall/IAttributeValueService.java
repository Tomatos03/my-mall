package com.mall.api.service.mall;

import com.mall.dto.mall.AttributeValueDTO;
import com.mall.dto.condition.mall.AttributeValueConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface IAttributeValueService {

    /**
     * 新增
     */
    int insert(AttributeValueDTO dto);

    /**
     * 修改
     */
    int update(AttributeValueDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<AttributeValueDTO> searchByCondition(AttributeValueConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);
}
