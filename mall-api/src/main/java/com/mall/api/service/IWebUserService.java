package com.mall.api.service;

import com.mall.dto.AuthenticatedUserDTO;
import com.mall.dto.AuthenticationUserDTO;
import com.mall.dto.CaptchaDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
public interface IWebUserService {
    AuthenticatedUserDTO login(AuthenticationUserDTO authenticationUserDTO);

    CaptchaDTO getCode();

    AuthenticatedUserDTO getUserInfo();

    void logout(HttpServletRequest request);
}
