package com.mall.business.service.sys;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mall.api.properties.CaptchaProps;
import com.mall.api.properties.IPassWordProps;
import com.mall.api.properties.JwtProps;
import com.mall.api.service.sys.IWebUserService;
import com.mall.business.mapper.sys.CommonTaskMapper;
import com.mall.business.mapper.sys.UserMapper;
import com.mall.common.domain.JwtHelper;
import com.mall.common.domain.cache.CaptchaCacher;
import com.mall.common.domain.cache.TokenCacher;
import com.mall.common.domain.cache.UserCacher;
import com.mall.common.enums.EmailBizTypeEnum;
import com.mall.common.enums.TaskStatusEnum;
import com.mall.common.enums.TaskTypeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.*;
import com.mall.dto.sys.AuthenticatedUserDTO;
import com.mall.dto.sys.AuthenticationUserDTO;
import com.mall.dto.sys.CaptchaDTO;
import com.mall.entity.sys.CommonTaskDO;
import com.mall.entity.sys.UserDO;
import com.mall.pojo.RemoteUserPOJO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
@Slf4j
@Service
public class WebUserService implements IWebUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProps jwtProps;
    @Autowired
    private CaptchaProps captchaProps;
    @Autowired
    private CommonTaskMapper commonTaskMapper;
    @Autowired
    private IPassWordProps passWordProps;

    @Override
    public AuthenticatedUserDTO login(AuthenticationUserDTO authenticationUserDTO) {
        log.info("尝试登录:{}", authenticationUserDTO);

        CaptchaValidaUtil.validate(
                CaptchaCacher.get(authenticationUserDTO.getUuid()),
                authenticationUserDTO.getCode()
        );

        String username = authenticationUserDTO.getUsername();
        String decodePassword = PasswordHelper.decodeRsaPassword(
                authenticationUserDTO.getPassword(),
                passWordProps.privateKey()
        );

        Authentication authenticated = AuthenticatorUtil.authenticate(username, decodePassword);

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        // TODO: 本地测试127.0.0.1 无法查询到信息
        String ipStr = "123.58.180.7";
        //        String ipStr = IpUtil.getIpAddr(request);
        //        String ipStr = "8.8.8.8";
        String city = Ip2regionUtil.city(ipStr);

        validRemoteLogin(9L, city);

        AuthenticatedUserDTO authenticatedUserDTO = AuthenticatorUtil.convertFrom(authenticated);

        String token = JwtHelper.createUserToken(username, jwtProps);
        authenticatedUserDTO.setToken(token);

        UserCacher.save(authenticatedUserDTO, jwtProps.expiration());
        TokenCacher.save(username, token, jwtProps.expiration());

        updateLastLoginCity(city);
        return authenticatedUserDTO;
    }

    private void validRemoteLogin(Long userId, String nowLoginCity) {
        UserDO userDO = userMapper.findById(userId);
        String lastLoginCity = userDO.getLastLoginCity();

        if (StrUtil.isEmpty(lastLoginCity) || nowLoginCity.equals(lastLoginCity))
            return;

        addRemoteLoginEmailTask(userDO, nowLoginCity);

        throw new BusinessException("您的账号处于异地登录，为了安全考虑，请修改密码之后重新登录");
    }

    private void addRemoteLoginEmailTask(UserDO userDO, String nowLoginCity) {
        RemoteUserPOJO remoteUserDO = getRemoteUserPOJO(userDO, nowLoginCity);

        String remoteUserJsonStr = JSONUtil.toJsonStr(remoteUserDO);

        CommonTaskDO commonTaskDO = CommonTaskDO.builder()
                                                .name("异地登录邮箱提醒")
                                                .type(TaskTypeEnum.SEND_EMAIL.getValue())
                                                .bizType(EmailBizTypeEnum.REMOTE_LOGIN.getValue())
                                                .status(TaskStatusEnum.WAITING.getValue())
                                                .failureCount(0)
                                                .requestParam(remoteUserJsonStr)
                                                .createUserId(9L)
                                                .createUserName("admin")
                                                .build();

        commonTaskMapper.insert(commonTaskDO);
    }

    private RemoteUserPOJO getRemoteUserPOJO(UserDO userDO, String nowCity) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();

        String ipStr = IpUtil.getIpAddr(request);
        String device = request.getHeader("user-agent");
        String nowTimeStr = TimeUtil.nowFormatted();

        return RemoteUserPOJO.builder()
                             .ip(ipStr)
                             .nickName(userDO.getNickName())
                             .email(userDO.getEmail())
                             .device(device)
                             .cityName(nowCity)
                             .loginTime(nowTimeStr)
                             .username(userDO.getUserName())
                             .build();
    }

    private void updateLastLoginCity(String city) {
        UserDO userDO = new UserDO();
        userDO.setId(9L);
        userDO.setLastLoginTime(LocalDateTime.now());
        userDO.setLastLoginCity(city);

        userMapper.update(userDO);
    }

    public AuthenticatedUserDTO getUserInfo() {
        return AuthenticatorUtil.getAuthenticatedUser();
    }

    public CaptchaDTO getCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(111, 36);
        String uuid = IdUtil.fastSimpleUUID();
        String imageBase64 = circleCaptcha.getImageBase64Data();

        CaptchaCacher.save(uuid, circleCaptcha.getCode(), captchaProps.expiration());
        return new CaptchaDTO(uuid, imageBase64);
    }

    public void logout(HttpServletRequest request) {
        String token = RequestUtil.getToken(request);
        String username = JwtHelper.getUsernameFromToken(token, jwtProps);

        UserCacher.del(username);
        TokenCacher.del(username);
    }
}
