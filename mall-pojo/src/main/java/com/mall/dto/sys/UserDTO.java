package com.mall.dto.sys;

import com.mall.entity.sys.RoleDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
@Schema(description = "用户传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends CommonDTO {
    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "部门ID")
    private Integer deptId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否删除，0-未删除，1-已删除")
    private Integer isDel;

    @Schema(description = "职位ID")
    private Integer jobId;

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

    @Schema(description = "最后登录城市")
    private String lastLoginCity;
}
