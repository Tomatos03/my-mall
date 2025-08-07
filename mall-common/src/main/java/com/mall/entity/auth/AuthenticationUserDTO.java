package com.mall.entity.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Schema(description = "认证用户传输传输对象")
@Data
public class AuthenticationUserDTO {
    /**
     * 唯一标识
     */
    @NotBlank(message = "唯一标识不能为空")
    @Schema(description = "唯一标识")
    private String uuid;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    @Schema(description = "用户名称")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    @NotBlank
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码")
    private String code;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;
}