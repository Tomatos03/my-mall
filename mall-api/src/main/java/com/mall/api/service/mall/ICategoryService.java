package com.mall.api.service.mall;

import com.mall.dto.mall.CategoryDTO;
import com.mall.dto.condition.mall.CategoryConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface ICategoryService {

    /**
     * 新增
     */
    int insert(CategoryDTO dto);

    /**
     * 修改
     */
    int update(CategoryDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<CategoryDTO> searchByCondition(CategoryConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);
}
