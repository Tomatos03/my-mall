package com.mall.business.mapper.sys;

import com.mall.entity.sys.UserDO;
import com.mall.dto.condition.sys.UserConditionDTO;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
public interface UserMapper extends CommonMapper<UserDO, UserConditionDTO> {
    /**
     * 通过id查询用户信息
     *
     * @param id 系统ID
     * @return 用户信息
     */
    UserDO findById(Long id);

    /**
     * 根据条件查询用户列表
     *
     * @param userConditionDTO 条件
     * @return 用户列表
     */
    List<UserDO> searchByCondition(UserConditionDTO userConditionDTO);

    /**
     * 根据条件查询用户数量
     *
     * @param userConditionEntity 条件
     * @return 用户列表
     */
//    int searchCount(UserConditionEntity userConditionEntity);

    /**
     * 添加用户
     *
     * @param userDO 用户实体
     * @return 影响行数
     */
    int insert(UserDO userDO);

    /**
     * 修改用户
     *
     * @param userDO 用户实体
     * @return 影响行数
     */
    int update(UserDO userDO);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);

    UserDO findByUserName(String username);

    List<UserDO> findByIds(List<Long> idList);

    int batchUpdateUserPwd(List<UserDO> users);
}
