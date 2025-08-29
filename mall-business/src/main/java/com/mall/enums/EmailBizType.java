package com.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@AllArgsConstructor
@Getter
public enum EmailBizType {
    REMOTE_LOGIN(1, "异地登录提醒");

    /**
     * 枚举值
     */
    private Integer value;


    /**
     * 枚举描述
     */
    private String desc;
}
