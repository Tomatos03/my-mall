package com.mall.business.service.mall;

import com.mall.dto.mall.AttributeDTO;
import com.mall.dto.sys.PageDTO;
import com.mall.entity.mall.AttributeDO;
import com.mall.api.service.mall.IAttributeService;
import com.mall.dto.condition.mall.AttributeConditionDTO;

import com.mall.business.mapper.mall.AttributeMapper;
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
public class AttributeService implements IAttributeService {
    @Autowired
    private AttributeMapper attributeMapper;

    @Override
    public int insert(AttributeDTO dto) {
        AttributeDO entity = BeanUtil.copyProperties(dto, AttributeDO.class);
        int affects = attributeMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(AttributeDTO dto) {
        AttributeDO entity = BeanUtil.copyProperties(dto, AttributeDO.class);
        return attributeMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return attributeMapper.delete(id);
    }

    @Override
    public List<AttributeDTO> searchByCondition(AttributeConditionDTO condition) {
        List<AttributeDO> entities = attributeMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, AttributeDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return attributeMapper.deleteByIds(ids);
    }

    @Override
    public AttributeDTO findById(Long id) {
        return null;
    }

    @Override
    public PageDTO<AttributeDO> searchByPage(AttributeConditionDTO condition) {
        return null;
    }
}
