package com.mall.business.service;

import com.mall.api.service.ITaskService;
import com.mall.business.mapper.CommonTaskMapper;
import com.mall.entity.CommonTaskDO;
import com.mall.dto.condition.CommonTaskConditionDTO;
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
public class TaskService implements ITaskService {
    @Autowired
    private CommonTaskMapper commonTaskMapper;

    @Override
    public void insert(CommonTaskDO commonTaskDO) {
        commonTaskMapper.insert(commonTaskDO);
    }

    @Override
    public void update(CommonTaskDO commonTaskDO) {
        commonTaskMapper.update(commonTaskDO);
    }

    @Override
    public List<CommonTaskDO> searchByCondition(CommonTaskConditionDTO commonTaskCondition) {
        return commonTaskMapper.searchByCondition(commonTaskCondition);
    }
}
