package com.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户角色关系传输对象")
public class UserRoleDTO {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "角色ID")
    private Long roleId;
}
