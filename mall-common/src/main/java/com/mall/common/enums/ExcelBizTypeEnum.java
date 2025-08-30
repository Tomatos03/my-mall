package com.mall.common.enums;

import com.mall.api.service.IDeptService;
import com.mall.api.service.IMenuService;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.dto.condition.MenuConditionDTO;
import com.mall.entity.DeptDO;
import com.mall.entity.MenuDO;
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
    DEPT(3, "部门", DeptConditionDTO.class, IDeptService.class, DeptDO.class);

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

