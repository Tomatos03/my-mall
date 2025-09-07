package com.mall.business.mapper.mall;

import com.mall.entity.mall.ProductDO;
import com.mall.dto.condition.mall.ProductConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Mapper
public interface ProductMapper {
    int insert(ProductDO entity);

    int update(ProductDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    List<ProductDO> searchByCondition(ProductConditionDTO condition);
}
