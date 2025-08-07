package com.mall.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.mall.vo.MenuTreeVO;
import com.mall.dto.MetaDTO;
import com.mall.entity.MenuDO;
import com.mall.entity.condition.ResponsePage;
import com.mall.entity.condition.MenuConditionDTO;
import com.mall.domain.page.PageCondition;
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

    public List<MenuTreeVO> getMenuTree() {
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setAlwaysShow(true);
        menuTreeVO.setId(0L);

        return getMenuTree0(menuTreeVO, true);
    }

    private List<MenuTreeVO> getMenuTree0(MenuTreeVO menuTreeVO, boolean isAlwaysShow) {
        MenuConditionDTO menuConditionDTO = new MenuConditionDTO();
        menuConditionDTO.setPageSize(PageCondition.NO_PAGINATION);
        menuConditionDTO.setPid(menuTreeVO.getId());

        List<MenuDO> menuEntities = menuMapper.searchByCondition(menuConditionDTO);
        if (CollectionUtil.isEmpty(menuEntities))
            return null;

        List<MenuTreeVO> menuTreeVOS = Lists.newArrayList();
        for (MenuDO menuDO : menuEntities) {
            MenuTreeVO newMenuTreeVO = buildMenuTreeDTO(menuDO, isAlwaysShow);
            menuTreeVOS.add(newMenuTreeVO);

            newMenuTreeVO.setChildren(getMenuTree0(newMenuTreeVO, false));
        }
        return menuTreeVOS;
    }


    private MenuTreeVO buildMenuTreeDTO(MenuDO menuDO, boolean isAlwaysShow) {
        MenuTreeVO menuTreeVO = BeanUtil.copyProperties(menuDO, MenuTreeVO.class);
        menuTreeVO.setAlwaysShow(isAlwaysShow);

        MetaDTO metaDTO = MetaDTO.builder()
                                 .icon(menuTreeVO.getIcon())
                                 .title(menuTreeVO.getName())
                                 .noCache(true)
                                 .build();

        menuTreeVO.setMeta(metaDTO);
        return menuTreeVO;
    }

    public ResponsePage<MenuDO> searchByPage(MenuConditionDTO menuConditionDTO) {
        List<MenuDO> menuEntities = menuMapper.searchByCondition(menuConditionDTO);
        return ResponsePage.build(menuConditionDTO, menuEntities.size(), menuEntities);
    }
}
