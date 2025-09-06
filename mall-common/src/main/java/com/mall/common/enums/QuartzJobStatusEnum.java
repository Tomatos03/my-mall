package com.mall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.mall.constant.MessageConst.UNSUPPORTED_TYPE;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
@AllArgsConstructor
@Getter
public enum QuartzJobStatusEnum {

    /**
     * 执行中
     */
    RUNNING(1, "执行中"),

    /**
     * 暂停
     */
    PAUSE(2, "暂停"),

    /**
     * 成功
     */
    SUCCESS(3, "成功"),

    /**
     * 失败
     */
    FAILURE(4, "失败");

    private Integer value;

    private String desc;

    public static QuartzJobStatusEnum of(Integer value){
        return Arrays.stream(values())
                     .filter(e-> e.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(()->new IllegalArgumentException(UNSUPPORTED_TYPE));
    }
}
