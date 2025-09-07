package com.mall.business.service.mall;

import com.mall.dto.mall.AttributeValueDTO;
import com.mall.entity.mall.AttributeValueDO;
import com.mall.api.service.mall.IAttributeValueService;
import com.mall.dto.condition.mall.AttributeValueConditionDTO;

import com.mall.business.mapper.mall.AttributeValueMapper;
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
public class AttributeValueService implements IAttributeValueService {
    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Override
    public int insert(AttributeValueDTO dto) {
        AttributeValueDO entity = BeanUtil.copyProperties(dto, AttributeValueDO.class);
        int affects = attributeValueMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(AttributeValueDTO dto) {
        AttributeValueDO entity = BeanUtil.copyProperties(dto, AttributeValueDO.class);
        return attributeValueMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return attributeValueMapper.delete(id);
    }

    @Override
    public List<AttributeValueDTO> searchByCondition(AttributeValueConditionDTO condition) {
        List<AttributeValueDO> entities = attributeValueMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, AttributeValueDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return attributeValueMapper.deleteByIds(ids);
    }
}
