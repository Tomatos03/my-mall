package com.mall.business.mapper.mall;

import com.mall.entity.mall.CategoryDO;
import com.mall.dto.condition.mall.CategoryConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Mapper
public interface CategoryMapper {
    int insert(CategoryDO entity);

    int update(CategoryDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    List<CategoryDO> searchByCondition(CategoryConditionDTO condition);
}
