package com.mall.business.mapper.sys;

import com.mall.dto.condition.sys.CommonJobLogConditionDTO;
import com.mall.entity.sys.CommonJobLogDO;

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
