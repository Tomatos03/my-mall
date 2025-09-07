package com.mall.business.service.mall;

import com.mall.dto.mall.UnitDTO;
import com.mall.entity.mall.UnitDO;
import com.mall.api.service.mall.IUnitService;
import com.mall.dto.condition.mall.UnitConditionDTO;

import com.mall.business.mapper.mall.UnitMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.core.bean.BeanUtil;
import java.util.List;

/**
 * 服务层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@Service
public class UnitService implements IUnitService {
    @Autowired
    private UnitMapper unitMapper;

    @Override
    public int insert(UnitDTO dto) {
        UnitDO entity = BeanUtil.copyProperties(dto, UnitDO.class);
        int affects = unitMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(UnitDTO dto) {
        UnitDO entity = BeanUtil.copyProperties(dto, UnitDO.class);
        return unitMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return unitMapper.delete(id);
    }

    @Override
    public List<UnitDTO> searchByCondition(UnitConditionDTO condition) {
        List<UnitDO> entities = unitMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, UnitDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return unitMapper.deleteByIds(ids);
    }
}
