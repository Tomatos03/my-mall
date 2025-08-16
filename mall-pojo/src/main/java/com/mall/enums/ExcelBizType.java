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
    MENU(1, "com.mall.entity.MenuConditionDTO", "菜单"),
    ROLE(2, "com.mall.entity.RoleConditionDTO", "角色"),
    DEPT(3, "com.mall.entity.DeptConditionDTO", "部门"),
    USER(4, "com.mall.entity.UserConditionDTO", "用户"),
    JOB(5, "com.mall.entity.JobConditionDTO", "岗位");

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 请求参数实体
     */
    private String requestDO;

    /**
     * 枚举描述
     */
    private String desc;
}

