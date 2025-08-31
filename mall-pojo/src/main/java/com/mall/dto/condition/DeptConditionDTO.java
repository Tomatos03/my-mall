package com.mall.dto.condition;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptConditionDTO extends CommonConditionDTO {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * ids
     */
    @Schema(description = "ids")
    private List<Long> idList;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

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
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 创建人名称
     */
    @Schema(description = "创建人名称")
    private String createUserName;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateUserId;

    /**
     * 修改人名称
     */
    @Schema(description = "修改人名称")
    private String updateUserName;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 是否删除 1：已删除 0：未删除
     */
    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;

    /**
     * 是否查询树
     */
    @Schema(description = "是否查询树")
    private Boolean queryTree;
}
