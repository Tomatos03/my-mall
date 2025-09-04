package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.mall.api.service.IMenuService;
import com.mall.business.mapper.CommonMapper;
import com.mall.business.mapper.MenuMapper;
import com.mall.dto.MenuDTO;
import com.mall.dto.MetaDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.dto.condition.PageConditionDTO;
import com.mall.entity.MenuDO;
import com.mall.vo.MenuTreeVO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Service
public class MenuService
        extends CommonService<MenuDO, MenuDTO, MenuConditionDTO>
        implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    protected MenuService() {
        super(MenuDO.class);
    }

    public List<MenuTreeVO> getMenuTree() {
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setAlwaysShow(true);
        menuTreeVO.setId(0L);

        return getMenuTree0(menuTreeVO, true);
    }

    private List<MenuTreeVO> getMenuTree0(MenuTreeVO menuTreeVO, boolean isAlwaysShow) {
        MenuConditionDTO condition = new MenuConditionDTO();
        condition.setPageSize(PageConditionDTO.NO_PAGINATION);
        condition.setPid(menuTreeVO.getId());

        List<MenuDO> menus = menuMapper.searchByCondition(condition);
        if (CollectionUtil.isEmpty(menus))
            return null;

        List<MenuTreeVO> menuTreeVOS = Lists.newArrayList();
        for (MenuDO menu : menus) {
            MenuTreeVO newMenuTreeVO = buildMenuTreeDTO(menu, isAlwaysShow);
            menuTreeVOS.add(newMenuTreeVO);

            newMenuTreeVO.setChildren(getMenuTree0(newMenuTreeVO, false));
        }
        return menuTreeVOS;
    }

    private MenuTreeVO buildMenuTreeDTO(MenuDO menuDO, boolean isAlwaysShow) {
        MenuTreeVO menuTreeVO = BeanUtil.copyProperties(menuDO, MenuTreeVO.class);
        menuTreeVO.setAlwaysShow(isAlwaysShow);
        menuTreeVO.setLabel(menuDO.getName());

        MetaDTO metaDTO = MetaDTO.builder()
                                 .icon(menuTreeVO.getIcon())
                                 .title(menuTreeVO.getLabel())
                                 .noCache(true)
                                 .build();

        menuTreeVO.setMeta(metaDTO);
        return menuTreeVO;
    }

    /**
     * 导出Excel到浏览器下载
     *
     * @param response
     * @param menuCondition
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 11:53
     */
    public void export(HttpServletResponse response, MenuConditionDTO menuCondition) throws IOException {
//        super.export(menuConditionDTO, response, MenuDO.class, ExcelTitleConst.MENU_DATE);
    }

    @Override
    public List<MenuTreeVO> getMenu(MenuConditionDTO condition) {
        Long currentMenuId = condition.getPid();
        if (currentMenuId == null)
            currentMenuId = 0L;

        List<MenuDO> menus = queryNextLevelMenu(currentMenuId);

        List<Long> ids = menus.stream()
                              .map(MenuDO::getId)
                              .toList();

        MenuConditionDTO newCondition = new MenuConditionDTO();
        newCondition.setPidList(ids);
        List<MenuDO> menuDOS = menuMapper.searchByCondition(newCondition);
        Map<Long, List<MenuDO>> childMenusMap = menuDOS
                                                   .stream()
                                                   .collect(Collectors.groupingBy(MenuDO::getPid));

        List<MenuTreeVO> result = new ArrayList<>();
        for (MenuDO menu : menus) {
            MenuTreeVO menuTreeVO = buildMenuTreeDTO(menu, false);

            List<MenuDO> subMenus = childMenusMap.get(menu.getId());
            boolean isEmptySubMenus = CollectionUtils.isEmpty(subMenus);
            menuTreeVO.setLeaf(isEmptySubMenus);
            menuTreeVO.setSubCount(isEmptySubMenus ? 0 : subMenus.size());
            menuTreeVO.setHasChildren(isEmptySubMenus);

            result.add(menuTreeVO);
        }
        return result;
    }

    private List<Long> queryNextLevelMenuId(Long id) {
        MenuConditionDTO condition = new MenuConditionDTO();
        condition.setPid(id);

        return menuMapper.searchByCondition(condition)
                         .stream()
                         .map(MenuDO::getId)
                         .toList();
    }

    private List<MenuDO> queryNextLevelMenu(Long id) {
        MenuConditionDTO condition = new MenuConditionDTO();
        condition.setPid(id);

        return menuMapper.searchByCondition(condition);
    }

    @Override
    public List<Long> getChild(Long id) {
        List<Long> result = Lists.newArrayList(id);
        List<Long> ids = queryNextLevelMenuId(id);
        if (CollectionUtil.isNotEmpty(ids))
            result.addAll(ids);
        return result;
    }

    public int insert(MenuDTO menuDTO) {
        menuDTO.setCreateUserId(1L);
        menuDTO.setCreateUserName("admin");

        return super.insert(menuDTO);
    }

    @Override
    protected CommonMapper<MenuDO, MenuConditionDTO> getMapper() {
        return menuMapper;
    }
}
