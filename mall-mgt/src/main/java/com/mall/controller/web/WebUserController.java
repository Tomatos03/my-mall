package com.mall.controller.web;

import com.mall.annotation.NoLogin;
import com.mall.entity.CaptchaEntity;
import com.mall.entity.auth.AuthUserEntity;
import com.mall.entity.auth.TokenEntity;
import com.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Tag(name = "web用户操作", description = "web用户接口")
@RestController
@RequestMapping("/v1/web/user")
@Validated
public class WebUserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param authUserEntity 用户实体
     * @return 影响行数
     */
    @NoLogin
    @Operation(summary = "用户登录", description = "用户登录")
    @PostMapping("/login")
    public TokenEntity login(@Valid @RequestBody AuthUserEntity authUserEntity) {
        return userService.login(authUserEntity);
    }

    /**
     * 用户退出登录
     *
     * @param request 请求
     * @return 影响行数
     */
    @NoLogin
    @Operation(summary = "用户退出登录", description = "用户退出登录")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    @NoLogin
    @Operation(summary = "获取验证码", description = "获取验证码")
    @GetMapping(value = "/code")
    public CaptchaEntity getCode() {
        return userService.getCode();
    }
}
