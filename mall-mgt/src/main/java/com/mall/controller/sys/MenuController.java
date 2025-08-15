package com.mall.controller.sys;

import com.mall.annotation.NoLogin;
import com.mall.dto.MenuDTO;
import com.mall.entity.MenuDO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.domain.ResponsePage;
import com.mall.service.MenuService;
import com.mall.vo.MenuTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    /**
     * 批量删除菜单
     *
     * @param ids 菜单ID
     * @return 影响行数
     */
    @NoLogin
    @Operation(summary = "删除菜单", description = "删除菜单")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody @NotNull List<Long> ids) {
        return menuService.deleteByIds(ids);
    }

    @NoLogin
    @Operation(summary = "导出菜单数据", description = "导出菜单数据")
    @PostMapping("/export")
    public void export(HttpServletResponse response, MenuConditionDTO menuConditionDTO) throws IOException {
        menuService.export(response, menuConditionDTO);
    }

    @Operation(summary = "插入菜单", description = "插入菜单")
    @PostMapping("/insert")
    public int insert(@RequestBody MenuDTO menuDTO) {
        return menuService.insert(menuDTO);
    }

    @Operation(summary = "更新菜单", description = "更新菜单")
    @PostMapping("/update")
    public int update(@RequestBody MenuDTO menuDTO) {
        return menuService.update(menuDTO);
    }
}
