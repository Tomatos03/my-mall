package com.mall.api.service;

import com.mall.dto.PageDTO;
import com.mall.dto.UserDTO;
import com.mall.dto.condition.UserConditionDTO;
import com.mall.entity.UserDO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface IUserService extends ICommonService<UserDO, UserDTO, UserConditionDTO> {
    UserDO findByUserName(String username);

    PageDTO<UserDO> searchByPage(UserConditionDTO userConditionDTO);

    UserDTO findById(Long id);

    int resetPwd(@NotNull List<Long> ids);
}
