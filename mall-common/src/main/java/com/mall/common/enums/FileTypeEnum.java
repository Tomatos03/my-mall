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
@Getter
@AllArgsConstructor
public enum FileTypeEnum {
    FILE("file"),
    IMAGE("image");

    private final String typeName;

    public static FileTypeEnum of(String typeName) {
        return Arrays.stream(FileTypeEnum.values())
                     .filter(fileTypeEnum -> fileTypeEnum.typeName.equals(typeName))
                     .findFirst()
                     .orElseThrow(()->new IllegalArgumentException(UNSUPPORTED_TYPE));
    }
}
