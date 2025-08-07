package com.mall.controller.sys;

import com.mall.annotation.NoLogin;
import com.mall.vo.MenuTreeVO;
import com.mall.entity.MenuDO;
import com.mall.entity.condition.ResponsePage;
import com.mall.entity.condition.MenuConditionDTO;
import com.mall.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */

@RestController
@RequestMapping("/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @NoLogin
    @Operation(summary = "获取菜单树", description = "获取菜单树")
    @GetMapping("/getMenuTree")
    public List<MenuTreeVO> getMenuTree() {
        return menuService.getMenuTree();
    }

    @NoLogin
    @Operation(summary = "根据条件查询菜单列表", description = "根据条件查询菜单列表")
    @PostMapping("/searchByPage")
    public ResponsePage<MenuDO> searchByPage(@RequestBody MenuConditionDTO menuConditionDTO) {
        return menuService.searchByPage(menuConditionDTO);
    }
}
