package com.mall.business.mapper.sys;

import com.mall.entity.sys.NotifyDO;
import com.mall.dto.condition.sys.NotifyConditionDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */
public interface CommonNotifyMapper extends CommonMapper<NotifyDO, NotifyConditionDTO> {
    @Override
    List<NotifyDO> searchByCondition(NotifyConditionDTO notifyCondition) throws DataAccessException;

    int update(NotifyDO notifyDO);

    int insert(NotifyDO notifyDO);
}
