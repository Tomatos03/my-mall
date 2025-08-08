package com.mall.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDO extends BaseDO{
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限")
    private String dataScope;

    /**
     * 角色级别
     */
    @Schema(description = "角色级别")
    private Integer level;

    /**
     * 功能权限
     */
    @Schema(description = "功能权限")
    private String permission;

    /**
     * 菜单
     */
    @Schema(description = "菜单")
    private List<MenuDO> menus;
}
