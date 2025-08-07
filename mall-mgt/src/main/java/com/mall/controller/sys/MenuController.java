package com.mall.controller.sys;

import com.mall.annotation.NoLogin;
import com.mall.dto.MenuTreeDTO;
import com.mall.entity.MenuEntity;
import com.mall.entity.ResponsePageEntity;
import com.mall.entity.condition.MenuConditionEntity;
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
    public List<MenuTreeDTO> getMenuTree() {
        return menuService.getMenuTree();
    }

    @NoLogin
    @Operation(summary = "根据条件查询菜单列表", description = "根据条件查询菜单列表")
    @PostMapping("/searchByPage")
    public ResponsePageEntity<MenuEntity> searchByPage(@RequestBody MenuConditionEntity menuConditionEntity) {
        return menuService.searchByPage(menuConditionEntity);
    }
}
