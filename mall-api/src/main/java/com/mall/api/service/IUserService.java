package com.mall.api.service;

import com.mall.dto.AuthenticatedUserDTO;
import com.mall.dto.AuthenticationUserDTO;
import com.mall.dto.CaptchaDTO;
import com.mall.entity.UserDO;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IUserService {
    UserDO findByUserName(String username);

    AuthenticatedUserDTO login(AuthenticationUserDTO authenticationUserDTO);

    CaptchaDTO getCode();

    AuthenticatedUserDTO getUserInfo();

    void logout(HttpServletRequest request);
}
