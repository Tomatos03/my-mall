package com.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
@Schema(description = "部门实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeptDTO extends BaseDTO {
    /**
     * 系统ID
     */
    @Schema(description = "系统ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 前端页面显示用的标签名称
     */
    @Schema(description = "标签名称")
    private String label;

    /**
     * 上级部门
     */
    @Schema(description = "上级部门")
    private Long pid;

    /**
     * 有效状态 1:有效 0:无效
     */
    @Schema(description = "有效状态 1:有效 0:无效")
    private Integer validStatus;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 是否叶子节点
     */
    @Schema(description = "是否叶子节点")
    private Boolean leaf;

    /**
     * 是否有下一级
     */
    @Schema(description = "是否有下一级")
    private Boolean hasChildren;

    /**
     * 下一级数量
     */
    @Schema(description = "下一级数量")
    private Integer subCount;

    /**
     * 部门层级
     */
    @Schema(description = "部门层级")
    private Integer level;

    /**
     * 子部门
     */
    @Schema(description = "子部门")
    private List<DeptDTO> children;
}
