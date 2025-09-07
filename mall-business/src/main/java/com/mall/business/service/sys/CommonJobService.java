package com.mall.business.service.sys;

import cn.hutool.core.bean.BeanUtil;
import com.mall.api.service.sys.ICommonJobService;
import com.mall.business.mapper.sys.CommonJobMapper;
import com.mall.dto.sys.CommonJobDTO;
import com.mall.dto.condition.sys.CommonJobConditionDTO;
import com.mall.entity.sys.CommonJobDO;
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
    public int insert(CommonJobDTO dto) {
        CommonJobDO commonJobDO = BeanUtil.copyProperties(dto, CommonJobDO.class);
        return commonJobMapper.insert(commonJobDO);
    }

    @Override
    public int update(CommonJobDTO entity) {
        // TODO 临时方法
        return 0;
    }

    @Override
    public List<CommonJobDO> searchByCondition(CommonJobConditionDTO condition) {
        return commonJobMapper.searchByCondition(condition);
    }
}
