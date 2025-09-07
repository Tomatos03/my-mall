package com.mall.business.service.mall;

import com.mall.dto.mall.CategoryDTO;
import com.mall.entity.mall.CategoryDO;
import com.mall.api.service.mall.ICategoryService;
import com.mall.dto.condition.mall.CategoryConditionDTO;

import com.mall.business.mapper.mall.CategoryMapper;
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
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int insert(CategoryDTO dto) {
        CategoryDO entity = BeanUtil.copyProperties(dto, CategoryDO.class);
        int affects = categoryMapper.insert(entity);
        return affects;
    }

    @Override
    public int update(CategoryDTO dto) {
        CategoryDO entity = BeanUtil.copyProperties(dto, CategoryDO.class);
        return categoryMapper.update(entity);
    }

    @Override
    public int delete(Long id) {
        return categoryMapper.delete(id);
    }

    @Override
    public List<CategoryDTO> searchByCondition(CategoryConditionDTO condition) {
        List<CategoryDO> entities = categoryMapper.searchByCondition(condition);
        return entities.stream()
                       .map(entity -> BeanUtil.copyProperties(entity, CategoryDTO.class))
                       .toList();
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return categoryMapper.deleteByIds(ids);
    }
}
