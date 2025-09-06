package com.mall.business.mapper;

import com.mall.dto.condition.CommonJobLogConditionDTO;
import com.mall.entity.CommonJobLogDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
public interface CommonJobLogMapper {
    List<CommonJobLogDO> searchByCondition(CommonJobLogConditionDTO condition);

    int delete(Long id);

    int update(CommonJobLogDO entity);

    int insert(CommonJobLogDO entity);

    int deleteByIds(List<Long> ids);
}
