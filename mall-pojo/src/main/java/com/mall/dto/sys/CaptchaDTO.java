package com.mall.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaptchaDTO {
    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 验证码图片
     */
    private String img;
}
