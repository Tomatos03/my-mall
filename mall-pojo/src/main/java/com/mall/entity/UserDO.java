package com.mall.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Schema(description = "用户传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDO extends CommonDO {
    @Schema(description = "头像ID")
    private Long avatarId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "部门实体")
    private DeptDO dept;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "职位ID")
    private Long jobId;

    @Schema(description = "上次修改密码时间，ISO8601格式")
    private String lastChangePasswordTime;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别，1-男，2-女")
    private Integer sex;

    @Schema(description = "有效状态，0-有效，1-无效")
    private Integer validStatus;

    @Schema(description = "角色ID列表")
    private List<RoleDO> roleList;

    /**
     * 岗位
     */
    @Schema(description = "岗位列表")
    private List<JobDO> jobList;

    @Schema(description = "上次登录城市")
    private String lastLoginCity;

    @Schema(description = "上次登录时间")
    private LocalDateTime lastLoginTime;
}
