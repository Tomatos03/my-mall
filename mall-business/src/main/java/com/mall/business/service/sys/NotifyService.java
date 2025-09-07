package com.mall.business.service.sys;

import com.mall.api.service.sys.INotifyService;
import com.mall.business.mapper.sys.CommonNotifyMapper;
import com.mall.entity.sys.NotifyDO;
import com.mall.dto.condition.sys.NotifyConditionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
@Service
public class NotifyService implements INotifyService {
    @Autowired
    private CommonNotifyMapper commonNotifyMapper;

    @Override
    public void addNotify(NotifyDO notifyDO) {
        commonNotifyMapper.insert(notifyDO);
    }

    @Override
    public List<NotifyDO> searchByCondition(NotifyConditionDTO notifyCondition) {
        return commonNotifyMapper.searchByCondition(notifyCondition);
    }

    @Override
    public void update(NotifyDO notifyDO) {
        commonNotifyMapper.update(notifyDO);
    }
}
