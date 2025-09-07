package com.mall.api.service.sys;

import com.mall.dto.sys.AuthenticatedUserDTO;
import com.mall.dto.sys.AuthenticationUserDTO;
import com.mall.dto.sys.CaptchaDTO;
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
