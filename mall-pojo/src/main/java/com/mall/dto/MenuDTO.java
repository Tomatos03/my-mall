package com.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
@Schema(description = "菜单传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO extends BaseDTO{
    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 上级菜单ID
     */
    @Schema(description = "上级菜单ID")
    private Long pid;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 路由
     */
    @Schema(description = "路由")
    private String path;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Boolean hidden;

    /**
     * 是否外链 1：是 0：否
     */
    @Schema(description = "是否外链 1：是 0：否")
    private Integer isLink;

    /**
     * 功能权限
     */
    @Schema(description = "功能权限")
    private String permission;

    /**
     * 类型 1：目录 2：菜单 3：按钮
     */
    @Schema(description = "类型 1：目录 2：菜单 3：按钮")
    private Integer type;

    /**
     * 组件
     */
    @Schema(description = "组件")
    private String component;

    /**
     * url地址
     */
    @Schema(description = "url地址")
    private String url;
}
