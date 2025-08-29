package com.mall.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mall.domain.CaptchaHelper;
import com.mall.domain.Ip2regionSearcher;
import com.mall.domain.JwtHelper;
import com.mall.domain.PasswordHelper;
import com.mall.domain.cache.CaptchaCacher;
import com.mall.domain.cache.TokenCacher;
import com.mall.domain.cache.UserCacher;
import com.mall.domain.security.Authenticator;
import com.mall.dto.AuthenticatedUserDTO;
import com.mall.dto.AuthenticationUserDTO;
import com.mall.dto.CaptchaDTO;
import com.mall.dto.UserDTO;
import com.mall.dto.condition.UserConditionDTO;
import com.mall.entity.CommonTaskDO;
import com.mall.enums.EmailBizType;
import com.mall.enums.TaskStatus;
import com.mall.enums.TaskType;
import com.mall.pojo.RemoteUserPOJO;
import com.mall.entity.UserDO;
import com.mall.entity.UserRoleDO;
import com.mall.exception.BusinessException;
import com.mall.mapper.CommonTaskMapper;
import com.mall.mapper.UserMapper;
import com.mall.mapper.UserRoleMapper;
import com.mall.util.IpUtil;
import com.mall.util.RequestUtil;
import com.mall.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private Ip2regionSearcher ip2regionSearcher;
    @Autowired
    private CommonTaskMapper commonTaskMapper;


    /**
     * 通过id查询用户信息
     *
     * @param id 系统ID
     * @return 用户信息
     */
    public UserDTO findById(Long id) {
        UserDO userDO = userMapper.findById(id);
        return BeanUtil.copyProperties(userDO, UserDTO.class);
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
     * @param userDTO 用户实体
     * @return 影响行数
     */
    @Transactional(rollbackFor = Throwable.class)
    public void insert(UserDTO userDTO) {
        UserDO userDO = BeanUtil.copyProperties(userDTO, UserDO.class);

        hasDuplicateUserName(userDO.getUserName());
        hasDuplicateEmail(userDO.getEmail());

        userMapper.insert(userDO);
        userRoleMapper.batchInsert(UserRoleDO.buildUserRoleDO(userDO));
    }

    private void hasDuplicateEmail(String email) {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setEmail(email);

        List<UserDO> userDOS = userMapper.searchByCondition(userConditionDTO);
        if (!CollectionUtil.isEmpty(userDOS))
            throw new BusinessException("重复的邮箱");
    }

    private void hasDuplicateUserName(String username) {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setUserName(username);

        List<UserDO> userDOS = userMapper.searchByCondition(userConditionDTO);
        if (!CollectionUtil.isEmpty(userDOS))
            throw new BusinessException("重复的用户名");
    }

    /**
     * 修改用户
     *
     * @param userDTO 用户实体
     * @return 影响行数
     */
    public int update(UserDTO userDTO) {
        UserDO userDO = BeanUtil.copyProperties(userDTO, UserDO.class);
        return userMapper.update(userDO);
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

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        // TODO: 本地测试127.0.0.1 无法查询到信息
//        String ipStr = IpUtil.getIpAddr(request);
//        String ipStr = "123.58.180.7";
        String ipStr = "8.8.8.8";
        String[] ipInfo = ip2regionSearcher.queryIpInfo(ipStr);
        String city = ip2regionSearcher.getCity(ipInfo);

        validRemoteLogin(9L, city);

        AuthenticatedUserDTO authenticatedUserDTO = Authenticator.convertFrom(authenticated);

        String token = jwtHelper.createUserToken(username);
        authenticatedUserDTO.setToken(token);

        userCacher.save(authenticatedUserDTO);
        tokenCacher.save(username, token);

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
                                                .type(TaskType.SEND_EMAIL.getValue())
                                                .bizType(EmailBizType.REMOTE_LOGIN.getValue())
                                                .status(TaskStatus.WAITING.getValue())
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

    public void logout(HttpServletRequest request) {
        String token = RequestUtil.getToken(request);
        String username = jwtHelper.getUsernameFromToken(token);

        userCacher.del(username);
        tokenCacher.del(username);
    }

    public AuthenticatedUserDTO getUserInfo() {
        return authenticator.getAuthenticatedUser();
    }

    public CaptchaDTO getCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(111, 36);
        String uuid = IdUtil.fastSimpleUUID();
        String imageBase64 = circleCaptcha.getImageBase64Data();

        captchaCacher.save(uuid, circleCaptcha.getCode());
        return new CaptchaDTO(uuid, imageBase64);
    }
}
