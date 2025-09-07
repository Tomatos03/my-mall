package com.mall.business.mapper.mall;

import com.mall.entity.mall.AttributeValueDO;
import com.mall.dto.condition.mall.AttributeValueConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 数据访问层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Mapper
public interface AttributeValueMapper {
    int insert(AttributeValueDO entity);

    int update(AttributeValueDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    List<AttributeValueDO> searchByCondition(AttributeValueConditionDTO condition);
}
