package com.mall.service;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.mall.dto.MenuTreeDTO;
import com.mall.dto.MetaDTO;
import com.mall.entity.MenuEntity;
import com.mall.entity.ResponsePageEntity;
import com.mall.entity.condition.MenuConditionEntity;
import com.mall.entity.condition.PageConditionEntity;
import com.mall.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    public List<MenuTreeDTO> getMenuTree() {
        MenuTreeDTO menuTreeDTO = new MenuTreeDTO();
        menuTreeDTO.setAlwaysShow(true);
        menuTreeDTO.setId(0L);

        return getMenuTree0(menuTreeDTO, true);
    }

    private List<MenuTreeDTO> getMenuTree0(MenuTreeDTO menuTreeDTO, boolean isAlwaysShow) {
        MenuConditionEntity menuConditionEntity = new MenuConditionEntity();
        menuConditionEntity.setPageSize(PageConditionEntity.NO_PAGINATION);
        menuConditionEntity.setPid(menuTreeDTO.getId());

        List<MenuEntity> menuEntities = menuMapper.searchByCondition(menuConditionEntity);
        if (menuEntities == null || menuEntities.isEmpty())
            return null;

        List<MenuTreeDTO> menuTreeDTOS = Lists.newArrayList();
        for (MenuEntity menuEntity : menuEntities) {
            MenuTreeDTO newMenuTreeDTO = buildMenuTreeDTO(menuEntity, isAlwaysShow);
            menuTreeDTOS.add(newMenuTreeDTO);

            newMenuTreeDTO.setChildren(getMenuTree0(newMenuTreeDTO, false));
        }
        return menuTreeDTOS;
    }


    private MenuTreeDTO buildMenuTreeDTO(MenuEntity menuEntity, boolean isAlwaysShow) {
        MenuTreeDTO menuTreeDTO = BeanUtil.copyProperties(menuEntity, MenuTreeDTO.class);
        menuTreeDTO.setAlwaysShow(isAlwaysShow);

        MetaDTO metaDTO = new MetaDTO();
        metaDTO.setIcon(menuTreeDTO.getIcon());
        metaDTO.setTitle(menuTreeDTO.getName());
        metaDTO.setNoCache(true);

        menuTreeDTO.setMeta(metaDTO);
        return menuTreeDTO;
    }

    public ResponsePageEntity<MenuEntity> searchByPage(MenuConditionEntity menuConditionEntity) {
        List<MenuEntity> menuEntities = menuMapper.searchByCondition(menuConditionEntity);
        return ResponsePageEntity.build(menuConditionEntity, menuEntities.size(), menuEntities);
    }
}
