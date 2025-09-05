package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.IUserService;
import com.mall.business.mapper.CommonMapper;
import com.mall.common.domain.assembler.UserAssembler;
import com.mall.common.util.*;
import com.mall.dto.*;
import com.mall.dto.condition.UserConditionDTO;
import com.mall.entity.JobDO;
import com.mall.entity.UserDO;
import com.mall.entity.UserRoleDO;
import com.mall.common.exception.BusinessException;
import com.mall.business.mapper.UserMapper;
import com.mall.business.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mall.constant.UserConst.DEFAULT_PASSWORD;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@Service
public class UserService
        extends CommonService<UserDO, UserDTO, UserConditionDTO>
        implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserAssembler userAssembler;

    protected UserService() {
        super(UserDO.class);
    }

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
     * 批量重置用户的密码
     *
     * @param ids 用户id列表
     * @return int 成功重置密码的用户数
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/9/4 20:58
     */
    @Override
    public int resetPwd(List<Long> ids) {
        List<UserDO> users = userMapper.findByIds(ids);
        if (CollectionUtil.isEmpty(users))
            return 0;

        users.forEach(user -> user.setPassword(DEFAULT_PASSWORD));

        return userMapper.batchUpdateUserPwd (users);
    }

    /**
     * 添加用户
     *
     * @param userDTO 用户实体
     * @return 影响行数
     */
    @Transactional(rollbackFor = Throwable.class)
    public int insert(UserDTO userDTO) {
        UserDO user = BeanUtil.copyProperties(userDTO, UserDO.class);

        checkDuplicateUserName(user.getUserName());
        checkDuplicateEmail(user.getEmail());
        setDefaultPasswordIfNotSet(user);
        PasswordHelper.encode(user.getPassword());
        fillDeptAndJobId(user);

        userRoleMapper.batchInsert(UserRoleDO.buildUserRoleDO(user));
        return userMapper.insert(user);
    }

    private void fillDeptAndJobId(UserDO user) {
        if (user.getDept() != null)
            user.setDeptId(user.getDept().getId());

        List<JobDO> jobList = user.getJobList();
        if (CollectionUtil.isNotEmpty(jobList))
            user.setJobId(jobList.get(0).getId());
    }

    private void setDefaultPasswordIfNotSet(UserDO user) {
        if (user.getPassword() == null)
            user.setPassword(DEFAULT_PASSWORD);
    }

    /**
     * 判断邮箱是否重复
     *
     * @param email 邮箱
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 11:31
     */
    private void checkDuplicateEmail(String email) {
        UserConditionDTO userConditionDTO = new UserConditionDTO();
        userConditionDTO.setEmail(email);

        List<UserDO> userDOS = userMapper.searchByCondition(userConditionDTO);
        if (!CollectionUtil.isEmpty(userDOS))
            throw new BusinessException("重复的邮箱");
    }

    /**
     * 判断用户名是否重复
     *
     * @param username
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 11:31
     */
    private void checkDuplicateUserName(String username) {
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
        rebuildUserRoleRelation(userDTO);
        return super.update(userDTO);
    }

    private void rebuildUserRoleRelation(UserDTO userDTO) {
        userRoleMapper.deleteByUserId(userDTO.getId());

        List<UserRoleDO> userRoleList =
                UserRoleDO.buildUserRoleDO(BeanUtil.copyProperties(userDTO, UserDO.class));

        userRoleMapper.batchInsert(userRoleList);
    }

    @Override
    public UserDO findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    protected CommonMapper<UserDO, UserConditionDTO> getMapper() {
        return this.userMapper;
    }

    @Override
    public PageDTO<UserDO> searchByPage(UserConditionDTO userConditionDTO) {
        List<UserDO> users = userMapper.searchByCondition(userConditionDTO);
        if (CollectionUtil.isEmpty(users))
            return PageUtil.emptyPage();

        userAssembler.assemblerDept(users);
        userAssembler.assemblerRole(users);
        userAssembler.assemblerJob(users);

        return PageUtil.buildPageDTO(userConditionDTO, users);
    }
}
