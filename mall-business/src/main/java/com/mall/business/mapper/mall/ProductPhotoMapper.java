package com.mall.business.mapper.mall;

import com.mall.entity.mall.ProductPhotoDO;
import com.mall.dto.condition.mall.ProductPhotoConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Mapper
public interface ProductPhotoMapper {
    int insert(ProductPhotoDO entity);

    int update(ProductPhotoDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    List<ProductPhotoDO> searchByCondition(ProductPhotoConditionDTO condition);
}
