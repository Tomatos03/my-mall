package com.mall.business.mapper.mall;

import com.mall.entity.mall.BrandDO;
import com.mall.dto.condition.mall.BrandConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Mapper
public interface BrandMapper {
    int insert(BrandDO entity);

    int update(BrandDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    List<BrandDO> searchByCondition(BrandConditionDTO condition);
}
