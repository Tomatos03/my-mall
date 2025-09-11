package com.mall.business.mapper.common;

import com.mall.dto.condition.mall.SensitiveWordConditionDTO;
import com.mall.entity.SensitiveWordDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */
public interface SensitiveWordMapper {
    int insert(SensitiveWordDO entity);

    int update(SensitiveWordDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    void batchInsert(List<SensitiveWordDO> list);

    List<SensitiveWordDO> searchByCondition(SensitiveWordConditionDTO condition);
}
