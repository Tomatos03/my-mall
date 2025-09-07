package com.mall.business.service.mall;

import com.mall.dto.mall.ProductPhotoDTO;
import com.mall.entity.mall.ProductPhotoDO;
import com.mall.api.service.mall.IProductPhotoService;
import com.mall.dto.condition.mall.ProductPhotoConditionDTO;

import com.mall.business.mapper.mall.ProductPhotoMapper;
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
public class ProductPhotoService implements IProductPhotoService {
    @Autowired
    private ProductPhotoMapper productPhotoMapper;

    @Override
    public int insert(ProductPhotoDTO dto) {
        ProductPhotoDO entity = BeanUtil.copyProperties(dto, ProductPhotoDO.class);
        int affects = productPhotoMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(ProductPhotoDTO dto) {
        ProductPhotoDO entity = BeanUtil.copyProperties(dto, ProductPhotoDO.class);
        return productPhotoMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return productPhotoMapper.delete(id);
    }

    @Override
    public List<ProductPhotoDTO> searchByCondition(ProductPhotoConditionDTO condition) {
        List<ProductPhotoDO> entities = productPhotoMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, ProductPhotoDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return productPhotoMapper.deleteByIds(ids);
    }
}
