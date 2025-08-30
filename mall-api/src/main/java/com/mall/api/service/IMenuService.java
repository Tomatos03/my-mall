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
public interface IMenuService extends ICommonService<MenuDO, MenuConditionDTO>{
    List<MenuTreeVO> getMenuTree();

    int deleteByIds(List<Long> ids);

    void export(HttpServletResponse response, MenuConditionDTO menuConditionDTO) throws IOException;

    int insert(MenuDTO menuDTO);

    int update(MenuDTO menuDTO);
}
