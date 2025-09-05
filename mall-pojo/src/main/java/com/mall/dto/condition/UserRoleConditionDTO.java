package com.mall.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
@Schema(description = "用户角色关联查询条件实体")
@Data
public class UserRoleConditionDTO extends CommonConditionDTO {
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户ID集合")
    private List<Long> userIdList;

    @Schema(description = "角色ID")
    private Long roleId;
}