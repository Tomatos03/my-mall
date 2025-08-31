package com.mall.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
@Schema(description = "岗位条件传输对象")
@Data
public class JobConditionDTO extends CommonConditionDTO{
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "岗位名称")
    private String name;

    @Schema(description = "岗位排序")
    private Integer sort;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "有效状态 1:有效 0:无效")
    private Integer validStatus;

    @Schema(description = "创建人ID")
    private Long createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    @Schema(description = "修改人ID")
    private Long updateUserId;

    @Schema(description = "修改人名称")
    private String updateUserName;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;
}
