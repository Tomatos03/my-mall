package com.mall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Getter
@AllArgsConstructor
public enum JobResultEnum {
    SUCCESS(1, "成功"),
    FAIL(0, "失败");

    private final Integer code;
    private final String desc;

    public static JobResultEnum fromCode(Integer code) {
        for (JobResultEnum value : JobResultEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无法转换的类型");
    }
}
