package com.mall.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.IUserService;
import com.mall.common.domain.assembler.UserDOAssembler;
import com.mall.common.util.*;
import com.mall.dto.*;
import com.mall.dto.condition.UserConditionDTO;
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
    private UserDOAssembler userDOAssembler;

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

    /**
     * 判断邮箱是否重复
     *
     * @param email 邮箱
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/31 11:31
     */
    private void hasDuplicateEmail(String email) {
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

    @Override
    public UserDO findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public PageDTO<UserDO> searchByPage(UserConditionDTO userConditionDTO) {
        List<UserDO> users = userMapper.searchByCondition(userConditionDTO);
        if (CollectionUtil.isEmpty(users))
            return PageUtil.emptyPage();

        return PageUtil.buildPageDTO(userConditionDTO, userDOAssembler.assemblerDept(users));
    }
}
