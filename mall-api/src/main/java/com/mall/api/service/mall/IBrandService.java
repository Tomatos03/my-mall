package com.mall.api.service.mall;

import com.mall.dto.mall.BrandDTO;
import com.mall.dto.condition.mall.BrandConditionDTO;
import java.util.List;

/**
 *
 *
 * @author Tomatos
 * @date 2025-09-07
 */
public interface IBrandService {

    /**
     * 新增
     */
    int insert(BrandDTO dto);

    /**
     * 修改
     */
    int update(BrandDTO dto);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 条件查询
     */
    List<BrandDTO> searchByCondition(BrandConditionDTO condition);

    /**
     * 批量删除
     */
    int deleteByIds(List<Long> ids);
}
