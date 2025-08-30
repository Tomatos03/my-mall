package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.mall.api.service.IMenuService;
import com.mall.constant.ExcelTitleConst;
import com.mall.dto.MenuDTO;
import com.mall.dto.MetaDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.entity.MenuDO;
import com.mall.entity.condition.PageCondition;
import com.mall.business.mapper.BaseMapper;
import com.mall.business.mapper.MenuMapper;
import com.mall.vo.MenuTreeVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Service
public class MenuService extends CommonService<MenuDO, MenuConditionDTO> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

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

    public int deleteByIds(List<Long> ids) {
        return menuMapper.batchDelete(ids);
    }

    public void export(HttpServletResponse response, MenuConditionDTO menuConditionDTO) throws IOException {
        super.export(menuConditionDTO, response, MenuDO.class, ExcelTitleConst.MENU_DATE);
    }

    public int insert(MenuDTO menuDTO) {
        MenuDO menuDO = BeanUtil.copyProperties(menuDTO, MenuDO.class);
        // TODO 暂时写死
        menuDO.setCreateUserId(1L);
        menuDO.setCreateUserName("admin");

        return menuMapper.insert(menuDO);
    }

    public int update(MenuDTO menuDTO) {
        MenuDO menuDO = BeanUtil.copyProperties(menuDTO, MenuDO.class);
        return menuMapper.update(menuDO);
    }

    @Override
    protected BaseMapper<MenuDO, MenuConditionDTO> getMapper() {
        return menuMapper;
    }
}
