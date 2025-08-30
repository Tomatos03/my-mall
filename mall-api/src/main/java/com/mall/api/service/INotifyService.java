package com.mall.api.service;

import com.mall.entity.NotifyDO;
import com.mall.entity.condition.NotifyCondition;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface INotifyService {
    void addNotify(NotifyDO notifyDO);

    List<NotifyDO> searchByCondition(NotifyCondition notifyCondition);

    void update(NotifyDO notifyDO);
}
