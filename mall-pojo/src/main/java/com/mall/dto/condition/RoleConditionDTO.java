package com.mall.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
@Schema(description = "角色查询条件实体")
@Data
public class RoleConditionDTO extends CommonConditionDTO {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "数据权限")
    private String dataScope;

    @Schema(description = "角色级别")
    private Integer level;

    @Schema(description = "功能权限")
    private String permission;

    @Schema(description = "创建人ID")
    private Long createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "修改人ID")
    private Long updateUserId;

    @Schema(description = "修改人名称")
    private String updateUserName;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;

    private List<Long> idList;

    private String blurry;
}

