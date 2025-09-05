package com.mall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@AllArgsConstructor
@Getter
public enum FlowLimitResultEnum {
    LIMITED(1L, "被限流"),
    NOT_LIMITED(0L, "未被限流");

    private final Long code;
    private final String desc;

    public static FlowLimitResultEnum fromCode(Long code) {
        for (FlowLimitResultEnum value : FlowLimitResultEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无法转换的类型");
    }
}
