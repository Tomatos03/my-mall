package com.mall.web.controller.web;

import com.mall.api.service.IWebUserService;
import com.mall.security.annotation.NoLogin;
import com.mall.dto.AuthenticatedUserDTO;
import com.mall.dto.CaptchaDTO;
import com.mall.dto.AuthenticationUserDTO;
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
    private IWebUserService webUserService;

    /**
     * 用户登录
     *
     * @param authenticationUserDTO 认证用户
     * @return 影响行数
     */
    @NoLogin
    @Operation(summary = "用户登录", description = "用户登录")
    @PostMapping("/login")
    public AuthenticatedUserDTO login(@Valid @RequestBody AuthenticationUserDTO authenticationUserDTO) {
        return webUserService.login(authenticationUserDTO);
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
        webUserService.logout(request);
    }

    @NoLogin
    @Operation(summary = "获取验证码", description = "获取验证码")
    @GetMapping(value = "/code")
    public CaptchaDTO getCode() {
        return webUserService.getCode();
    }

    @NoLogin
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    @GetMapping(value = "/info")
    public AuthenticatedUserDTO getUserInfo() {
        return webUserService.getUserInfo();
    }
}
