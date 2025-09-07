package com.mall.api.service.sys;

import com.mall.dto.sys.PageDTO;
import com.mall.dto.sys.UserDTO;
import com.mall.dto.condition.sys.UserConditionDTO;
import com.mall.entity.sys.UserDO;
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
