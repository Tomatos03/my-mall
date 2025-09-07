package com.mall.api.service.sys;

import com.mall.entity.sys.NotifyDO;
import com.mall.dto.condition.sys.NotifyConditionDTO;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface INotifyService {
    void addNotify(NotifyDO notifyDO);

    List<NotifyDO> searchByCondition(NotifyConditionDTO notifyCondition);

    void update(NotifyDO notifyDO);
}
