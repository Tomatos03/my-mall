package com.mall.business.mapper.mall;

import com.mall.entity.mall.UnitDO;
import com.mall.dto.condition.mall.UnitConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Mapper
public interface UnitMapper {
    int insert(UnitDO entity);

    int update(UnitDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    List<UnitDO> searchByCondition(UnitConditionDTO condition);
}
