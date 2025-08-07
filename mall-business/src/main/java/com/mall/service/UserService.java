package com.mall.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import com.mall.domain.Authenticator;
import com.mall.domain.CaptchaHelper;
import com.mall.domain.JwtHelper;
import com.mall.domain.PasswordHelper;
import com.mall.domain.cache.CaptchaCacher;
import com.mall.domain.cache.TokenCacher;
import com.mall.domain.cache.UserCacher;
import com.mall.dto.AuthenticatedUserDTO;
import com.mall.entity.CaptchaDTO;
import com.mall.entity.UserEntity;
import com.mall.entity.auth.AuthenticationUserDTO;
import com.mall.mapper.UserMapper;
import com.mall.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private CaptchaHelper captchaHelper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserCacher userCacher;
    @Autowired
    private TokenCacher tokenCacher;
    @Autowired
    private CaptchaCacher captchaCacher;

    /**
     * 通过id查询用户信息
     *
     * @param id 系统ID
     * @return 用户信息
     */
    public UserEntity findById(Long id) {
        return userMapper.findById(id);
    }

//    /**
//     * 根据条件查询用户列表
//     *
//     * @param userConditionEntity 条件
//     * @return 用户列表
//     */
//    public ResponsePageEntity<UserEntity> searchByPage(UserConditionEntity userConditionEntity) {
//        int count = userMapper.searchCount(userConditionEntity);
//        if (count == 0) {
//            return ResponsePageEntity.buildEmpty(userConditionEntity);
//        }
//        List<UserEntity> userEntities = userMapper.searchByCondition(userConditionEntity);
//        return ResponsePageEntity.build(userConditionEntity, count, userEntities);
//    }


    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     * @return 影响行数
     */
    public int insert(UserEntity userEntity) {
        return userMapper.insert(userEntity);
    }

    /**
     * 修改用户
     *
     * @param userEntity 用户实体
     * @return 影响行数
     */
    public int update(UserEntity userEntity) {
        return userMapper.update(userEntity);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    public AuthenticatedUserDTO login(AuthenticationUserDTO authenticationUserDTO) {
        log.info("尝试登录:{}", authenticationUserDTO);

        captchaHelper.validate(captchaCacher.get(authenticationUserDTO.getUuid()),
                               authenticationUserDTO.getCode());

        String username = authenticationUserDTO.getUsername();
        String decodePassword = passwordHelper.decodeRsaPassword(authenticationUserDTO.getPassword());
        Authentication authenticated = authenticator.authenticate(username, decodePassword);
        AuthenticatedUserDTO authenticatedUserDTO = convertFrom(authenticated);

        String token = jwtHelper.createUserToken(username);
        authenticatedUserDTO.setToken(token);

        userCacher.save(authenticatedUserDTO);
        tokenCacher.save(username, token);
        return authenticatedUserDTO;
    }

    public void logout(HttpServletRequest request) {
        String token = RequestUtil.getToken(request);
        String username = jwtHelper.getUsernameFromToken(token);

        userCacher.del(username);
        tokenCacher.del(username);
    }

    private AuthenticatedUserDTO convertFrom(Authentication authentication) {
        // 如果 principal 是 UserDetails，可以直接拿信息
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities()
                                           .stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .toList();

        return AuthenticatedUserDTO.builder()
                                   .username(username)
                                   .authorities(roles)
                                   .build();
    }

    public String getUserInfo() {
        return authenticator.getUsername();
    }

    public CaptchaDTO getCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(111, 36);
        String uuid = IdUtil.fastSimpleUUID();
        String imageBase64 = circleCaptcha.getImageBase64Data();

        captchaCacher.save(uuid, circleCaptcha.getCode());
        return new CaptchaDTO(uuid, imageBase64);
    }
}
