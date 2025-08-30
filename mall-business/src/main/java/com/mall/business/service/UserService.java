package com.mall.business.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mall.api.properties.CaptchaProps;
import com.mall.api.properties.JwtProps;
import com.mall.api.service.IUserService;
import com.mall.business.mapper.DeptMapper;
import com.mall.common.util.Ip2regionUtil;
import com.mall.common.domain.JwtHelper;
import com.mall.common.domain.cache.CaptchaCacher;
import com.mall.common.domain.cache.TokenCacher;
import com.mall.common.domain.cache.UserCacher;
import com.mall.common.util.PasswordHelper;
import com.mall.common.util.*;
import com.mall.dto.*;
import com.mall.dto.condition.DeptConditionDTO;
import com.mall.dto.condition.UserConditionDTO;
import com.mall.entity.CommonTaskDO;
import com.mall.common.enums.EmailBizTypeEnum;
import com.mall.common.enums.TaskStatusEnum;
import com.mall.common.enums.TaskTypeEnum;
import com.mall.entity.DeptDO;
import com.mall.pojo.RemoteUserPOJO;
import com.mall.entity.UserDO;
import com.mall.entity.UserRoleDO;
import com.mall.common.exception.BusinessException;
import com.mall.business.mapper.CommonTaskMapper;
import com.mall.business.mapper.UserMapper;
import com.mall.business.mapper.UserRoleMapper;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private CommonTaskMapper commonTaskMapper;
    @Autowired
    private JwtProps jwtProps;
    @Autowired
    private CaptchaProps captchaProps;
    @Autowired
    private DeptMapper deptMapper;

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

        CaptchaValidaUtil.validate(
                CaptchaCacher.get(authenticationUserDTO.getUuid()),
                authenticationUserDTO.getCode()
        );

        String username = authenticationUserDTO.getUsername();
        String decodePassword = PasswordHelper.decodeRsaPassword(
                authenticationUserDTO.getPassword(),
                jwtProps.secret()
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

    public void logout(HttpServletRequest request) {
        String token = RequestUtil.getToken(request);
        String username = JwtHelper.getUsernameFromToken(token, jwtProps);

        UserCacher.del(username);
        TokenCacher.del(username);
    }

    @Override
    public PageDTO<UserDO> searchByPage(UserConditionDTO userConditionDTO) {
        List<UserDO> userDOS = userMapper.searchByCondition(userConditionDTO);
        if (CollectionUtil.isEmpty(userDOS))
            return PageUtil.emptyPage();

        fillDeptName(userDOS);
        return PageUtil.buildPageDTO(userConditionDTO, userDOS);
    }

    private void fillDeptName(List<UserDO> users) {
        if (CollectionUtil.isEmpty(users))
            return;

        List<Long> deptIds = getUsersDeptId(users);

        if (CollectionUtil.isEmpty(users))
            return;

        List<DeptDO> deptDOS = getDeptsByIds(deptIds);
        Map<Long, DeptDO> deptIdMap = buildDeptIdNameMap(deptDOS);

        users.forEach(user -> {
            DeptDO deptDO = deptIdMap.get(user.getDeptId());
            user.setDeptName(deptDO.getName());
            user.setDept(deptDO);
        });
    }

    private Map<Long, DeptDO> buildDeptIdNameMap(List<DeptDO> deptDOS) {
        return deptDOS.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(DeptDO::getId, (deptDO) -> deptDO));
    }

    private List<DeptDO> getDeptsByIds(List<Long> deptIds) {
        DeptConditionDTO condition = new DeptConditionDTO();
        condition.setIdList(deptIds);

        return deptMapper.searchByCondition(condition);
    }

    private List<Long> getUsersDeptId(List<UserDO> users) {
        return users.stream()
                    .filter(Objects::nonNull)
                    .map(UserDO::getDeptId)
                    .toList();
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

    @Override
    public UserDO findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
