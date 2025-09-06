package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import com.mall.api.service.ICommonJobLogService;
import com.mall.business.mapper.CommonJobLogMapper;
import com.mall.dto.CommonJobLogDTO;
import com.mall.dto.condition.CommonJobLogConditionDTO;
import com.mall.entity.CommonJobLogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层
 *
 * @author Tomatos
 * @date 2025-09-05
 */
@Service
public class CommonJobLogService implements ICommonJobLogService {
    @Autowired
    private CommonJobLogMapper commonJobLogMapper;

    @Override
    public int insert(CommonJobLogDTO dto) {
        CommonJobLogDO entity = BeanUtil.copyProperties(dto, CommonJobLogDO.class);
        return commonJobLogMapper.insert(entity);
    }

    @Override
    public int update(CommonJobLogDTO dto) {
        CommonJobLogDO entity = BeanUtil.copyProperties(dto, CommonJobLogDO.class);
        return commonJobLogMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return commonJobLogMapper.delete(id);
    }

    @Override
    public List<CommonJobLogDTO> searchByCondition(CommonJobLogConditionDTO condition) {
        List<CommonJobLogDO> entities = commonJobLogMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, CommonJobLogDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return commonJobLogMapper.deleteByIds(ids);
    }
}
