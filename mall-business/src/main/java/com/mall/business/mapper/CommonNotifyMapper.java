package com.mall.business.mapper;

import com.mall.entity.NotifyDO;
import com.mall.entity.condition.NotifyCondition;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */
public interface CommonNotifyMapper extends BaseMapper<NotifyDO, NotifyCondition> {
    @Override
    List<NotifyDO> searchByCondition(NotifyCondition notifyCondition) throws DataAccessException;

    int update(NotifyDO notifyDO);

    int insert(NotifyDO notifyDO);
}
