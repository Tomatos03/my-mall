package com.mall.business.service.mall;

import com.mall.dto.mall.BrandDTO;
import com.mall.entity.mall.BrandDO;
import com.mall.api.service.mall.IBrandService;
import com.mall.dto.condition.mall.BrandConditionDTO;

import com.mall.business.mapper.mall.BrandMapper;
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
public class BrandService implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public int insert(BrandDTO dto) {
        BrandDO entity = BeanUtil.copyProperties(dto, BrandDO.class);
        int affects = brandMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(BrandDTO dto) {
        BrandDO entity = BeanUtil.copyProperties(dto, BrandDO.class);
        return brandMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return brandMapper.delete(id);
    }

    @Override
    public List<BrandDTO> searchByCondition(BrandConditionDTO condition) {
        List<BrandDO> entities = brandMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, BrandDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return brandMapper.deleteByIds(ids);
    }
}
