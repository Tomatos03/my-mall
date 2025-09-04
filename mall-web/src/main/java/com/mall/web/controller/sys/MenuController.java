package com.mall.web.controller.sys;

import com.mall.api.service.IMenuService;
import com.mall.common.annotation.ExcelExport;
import com.mall.common.annotation.NoLogin;
import com.mall.common.annotation.NoRepeatSubmit;
import com.mall.dto.MenuDTO;
import com.mall.dto.PageDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.entity.MenuDO;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.vo.MenuTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */

@Tag(name = "菜单管理", description = "菜单管理相关操作")
@RestController
@RequestMapping("/v1/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @NoLogin
    @Operation(summary = "获取菜单树", description = "获取菜单树")
    @GetMapping("/getMenuTree")
    public List<MenuTreeVO> getMenuTree() {
        return menuService.getMenuTree();
    }

    /**
     * 获取逐级加载的菜单
     *
     * @return 菜单列表
     */
    @Operation(summary = "获取逐级加载的菜单", description = "获取逐级加载的菜单")
    @PostMapping("/getMenu")
    public List<MenuTreeVO> getMenu(@RequestBody MenuConditionDTO condition) {
        return menuService.getMenu(condition);
    }

    /**
     * 获取下级菜单
     *
     * @return 菜单列表
     */
    @Operation(summary = "获取下级菜单", description = "获取下级菜单")
    @GetMapping("/getChild")
    public List<Long> getChild(@RequestParam("id") Long id) {
        return menuService.getChild(id);
    }

    @NoLogin
    @Operation(summary = "根据条件查询菜单列表", description = "根据条件查询菜单列表")
    @PostMapping("/searchByPage")
    public PageDTO<MenuDO> searchByPage(@RequestBody MenuConditionDTO menuCondition) {
        return menuService.searchByPage(menuCondition);
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
    @NoRepeatSubmit
    @Operation(summary = "导出菜单数据", description = "导出菜单数据")
    @PostMapping("/export")
    @ExcelExport(ExcelBizTypeEnum.MENU)
    public void export() {}

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
