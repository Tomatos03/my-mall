package com.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Getter
@AllArgsConstructor
public enum ExcelBizType {
    MENU(1, "菜单", "com.mall.dto.condition.MenuConditionDTO", "com.mall.service.MenuService", "com.mall.entity.MenuDO"),
    ROLE(2, "角色", "com.mall.dto.condition.RoleConditionDTO", "com.mall.service.RoleService", "com.mall.entity.RoleDO"),
    DEPT(3, "部门", "com.mall.dto.condition.DeptConditionDTO", "com.mall.service.DeptService", "com.mall.entity.DeptDO"),
    USER(4, "用户", "com.mall.dto.condition.UserConditionDTO", "com.mall.service.UserService", "com.mall.entity.UserDO"),
    JOB(5, "岗位", "com.mall.dto.condition.JobConditionDTO", "com.mall.service.JobService", "com.mall.entity.JobDO");

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 枚举描述
     */
    private String desc;

    /**
     * 请求参数实体
     */
    private String dtoName;

    /**
     * Service类名称
     */
    private String serviceName;

    /**
     * DO类名称
     */
    private String doName;

    /**
     * 根据 value 获取对应的枚举类型
     */
    public static ExcelBizType fromValue(Integer value) {
        for (ExcelBizType type : ExcelBizType.values()) {
            if (type.getValue().equals(value))
                return type;
        }
        throw new IllegalArgumentException("Unknown TaskType value: " + value);
    }
}

