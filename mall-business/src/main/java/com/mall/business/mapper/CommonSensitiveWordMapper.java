package com.mall.business.mapper;

import com.mall.entity.CommonSensitiveWordDO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */
public interface CommonSensitiveWordMapper {
    int insert(CommonSensitiveWordDO entity);

    int update(CommonSensitiveWordDO entity);

    int delete(Long id);

    int deleteByIds(List<Long> ids);

    void batchInsert(List<CommonSensitiveWordDO> list);
}
