package com.mall.api.service.mall;

import com.mall.dto.mall.ProductDTO;
import com.mall.dto.condition.mall.ProductConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface IProductService {

    /**
     * 新增
     */
    int insert(ProductDTO dto);

    /**
     * 修改
     */
    int update(ProductDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<ProductDTO> searchByCondition(ProductConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);

    List<ProductDTO> generate(List<ProductDTO> productDTOList);
}
