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
public enum TaskType {

    EXCEL_EXPORT(1, "通用excel数据导出");

    /**
     * 枚举值
     */
    private Integer value;


    /**
     * 枚举描述
     */
    private String desc;
}
