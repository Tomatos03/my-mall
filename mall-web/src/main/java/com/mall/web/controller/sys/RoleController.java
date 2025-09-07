package com.mall.web.controller.sys;

import com.mall.api.service.sys.IRoleService;
import com.mall.common.annotation.ExcelExport;
import com.mall.security.annotation.NoLogin;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.dto.sys.PageDTO;
import com.mall.dto.sys.RoleDTO;
import com.mall.dto.condition.sys.RoleConditionDTO;
import com.mall.entity.sys.RoleDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
@Tag(name = "角色管理", description = "角色管理相关操作")
@RestController
@RequestMapping("/v1/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 通过id查询角色信息
     *
     * @param id 系统ID
     * @return 角色信息
     */
    @Operation(summary = "通过id查询角色信息", description = "通过id查询角色信息")
    @GetMapping("/findById")
    public RoleDO findById(Long id) {
        return roleService.findById(id);
    }

    /**
     * 根据条件查询角色列表
     *
     * @param condition 条件
     * @return 角色列表
     */
    @Operation(summary = "根据条件查询角色列表", description = "根据条件查询角色列表")
    @PostMapping("/searchByPage")
    public PageDTO<RoleDO> searchByPage(@RequestBody RoleConditionDTO condition) {
        return roleService.searchByPage(condition);
    }

    /**
     * 根据查询所有角色
     *
     * @return 角色列表
     */
    @NoLogin
    @Operation(summary = "根据查询所有角色", description = "根据查询所有角色")
    @GetMapping("/all")
    public List<RoleDO> queryAllRole() {
        return roleService.queryAllRole();
    }

    /**
     * 添加角色
     *
     * @param dto 角色数据传输对象
     * @return 影响行数
     */
    @Operation(summary = "添加角色", description = "添加角色")
    @PostMapping("/insert")
    public int insert(@RequestBody RoleDTO dto) {
        return roleService.insert(dto);
   }

    /**
     * 修改角色
     *
     * @param dto 角色实体
     * @return 影响行数
     */
    @Operation(summary = "修改角色", description = "修改角色")
    @PostMapping("/update")
    public int update(@RequestBody RoleDTO dto) {
        return roleService.update(dto);
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色ID
     * @return 影响行数
     */
    @Operation(summary = "批量删除角色", description = "批量删除角色")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody @NotNull List<Long> ids) {
        return roleService.deleteByIds(ids);
    }

    /**
     * 导出角色数据
     *
     * @return 影响行数
     */
    @ExcelExport(ExcelBizTypeEnum.ROLE)
    @Operation(summary = "导出角色数据", description = "导出角色数据")
    @PostMapping("/export")
    public void export(@RequestBody RoleConditionDTO condition) {
    }
}
