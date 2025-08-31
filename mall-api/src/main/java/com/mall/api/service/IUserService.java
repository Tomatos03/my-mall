package com.mall.api.service;

import com.mall.dto.PageDTO;
import com.mall.dto.condition.UserConditionDTO;
import com.mall.entity.UserDO;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IUserService {
    UserDO findByUserName(String username);

    PageDTO<UserDO> searchByPage(UserConditionDTO userConditionDTO);
}
