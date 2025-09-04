package com.mall.api.service;

import com.mall.dto.MenuDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.entity.MenuDO;
import com.mall.vo.MenuTreeVO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
public interface IMenuService extends ICommonService<MenuDO, MenuDTO, MenuConditionDTO>{
    List<MenuTreeVO> getMenuTree();

    void export(HttpServletResponse response, MenuConditionDTO menuCondition) throws IOException;

    List<MenuTreeVO> getMenu(MenuConditionDTO condition);

    List<Long> getChild(Long id);
}
