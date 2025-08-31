package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.mall.api.service.IMenuService;
import com.mall.dto.MenuDTO;
import com.mall.dto.MetaDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.entity.MenuDO;
import com.mall.dto.condition.PageConditionDTO;
import com.mall.business.mapper.CommonMapper;
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
        MenuConditionDTO menuCondition = new MenuConditionDTO();
        menuCondition.setPageSize(PageConditionDTO.NO_PAGINATION);
        menuCondition.setPid(menuTreeVO.getId());

        List<MenuDO> menuEntities = menuMapper.searchByCondition(menuCondition);
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
