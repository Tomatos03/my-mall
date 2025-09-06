package com.mall.business.service;

import com.mall.api.service.ICommonJobService;
import com.mall.business.mapper.CommonJobMapper;
import com.mall.dto.condition.CommonJobConditionDTO;
import com.mall.entity.CommonJobDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Service
public class CommonJobService implements ICommonJobService {
    @Autowired
    private CommonJobMapper commonJobMapper;

    @Override
    public int insert(CommonJobDO jobDO) {
        return commonJobMapper.insert(jobDO);
    }

    @Override
    public List<CommonJobDO> searchByCondition(CommonJobConditionDTO condition) {
        return commonJobMapper.searchByCondition(condition);
    }
}
