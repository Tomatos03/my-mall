package com.mall.dto.condition;

import com.mall.entity.condition.RequestCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Schema(description = "用户条件实体")
@Data
@NoArgsConstructor
public class UserConditionDTO extends RequestCondition {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * ID集合
     */
    private List<Long> idList;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private Long avatarId;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long jobId;

    /**
     * 最后修改密码的日期
     */
    @Schema(description = "最后修改密码的日期")
    private Date lastChangePasswordTime;

    /**
     * 别名
     */
    @Schema(description = "别名")
    private String nickName;

    /**
     * 性别 1：男 2：女
     */
    @Schema(description = "性别 1：男 2：女")
    private Integer sex;

    /**
     * 有效状态 1:有效 0:无效
     */
    @Schema(description = "有效状态 1:有效 0:无效")
    private Boolean validStatus;

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
     * 最后登录城市
     */
    @Schema(description = "最后登录城市")
    private String lastLoginCity;
}
