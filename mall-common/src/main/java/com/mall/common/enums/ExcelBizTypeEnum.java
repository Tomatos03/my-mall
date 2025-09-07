package com.mall.common.enums;

import com.mall.api.service.sys.IDeptService;
import com.mall.api.service.sys.IJobService;
import com.mall.api.service.sys.IMenuService;
import com.mall.api.service.sys.IUserService;
import com.mall.dto.condition.sys.DeptConditionDTO;
import com.mall.dto.condition.sys.JobConditionDTO;
import com.mall.dto.condition.sys.MenuConditionDTO;
import com.mall.dto.condition.sys.UserConditionDTO;
import com.mall.entity.sys.DeptDO;
import com.mall.entity.sys.JobDO;
import com.mall.entity.sys.MenuDO;
import com.mall.entity.sys.UserDO;
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
public enum ExcelBizTypeEnum {
    MENU(1, "菜单", MenuConditionDTO.class, IMenuService.class, MenuDO.class),
    ROLE(2, "角色", UserConditionDTO.class, IUserService.class, UserDO.class),
    DEPT(3, "部门", DeptConditionDTO.class, IDeptService.class, DeptDO.class),
    USER(4, "用户", UserConditionDTO.class, IUserService.class, UserDO.class),
    JOB(5, "岗位",JobConditionDTO .class, IJobService .class, JobDO .class);

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 枚举描述
     */
    private String desc;

    /**
     * DTO class对象
     */
    private Class<?> conditionClass;

    /**
     * Service class对象
     */
    private Class<?> serviceClass;

    /**
     * DO class对象
     */
    private Class<?> doClass;

    /**
     * 根据 value 获取对应的枚举类型
     */
    public static ExcelBizTypeEnum fromValue(Integer value) {
        for (ExcelBizTypeEnum type : ExcelBizTypeEnum.values()) {
            if (type.getValue().equals(value))
                return type;
        }
        throw new IllegalArgumentException("Unknown TaskType value: " + value);
    }
}

