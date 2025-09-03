package com.mall.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-03
 */
@Schema(description = "数据条件实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DictConditionDTO extends CommonConditionDTO {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 父字段ID
     */
    @Schema(description = "父字段ID")
    private Long parentId;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述")
    private String dictDescription;

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
    private Date createTime;

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
    private Date updateTime;

    /**
     * 是否删除 1：已删除 0：未删除
     */
    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;

    /**
     * 查询条件
     */
    @Schema(description = "查询条件")
    private String blurry;
}