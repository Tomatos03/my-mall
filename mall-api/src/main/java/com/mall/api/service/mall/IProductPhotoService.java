package com.mall.api.service.mall;

import com.mall.dto.mall.ProductPhotoDTO;
import com.mall.dto.condition.mall.ProductPhotoConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface IProductPhotoService {

    /**
     * 新增
     */
    int insert(ProductPhotoDTO dto);

    /**
     * 修改
     */
    int update(ProductPhotoDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<ProductPhotoDTO> searchByCondition(ProductPhotoConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);
}
