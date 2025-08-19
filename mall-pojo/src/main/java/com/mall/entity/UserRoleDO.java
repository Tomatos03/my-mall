package com.mall.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
@Schema(description = "用户角色关联实体")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class UserRoleDO extends BaseDO {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

    public static List<UserRoleDO> buildUserRoleDO(UserDO userDO) {
        return userDO.getRoleList()
                     .stream()
                     .map(role -> (UserRoleDO) UserRoleDO.builder()
                                                         .userId(userDO.getId())
                                                         .roleId(role.getId())
                                                         .build()
                     )
                     .toList();
    }
}
