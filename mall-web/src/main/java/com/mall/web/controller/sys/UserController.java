package com.mall.web.controller.sys;

import com.mall.api.service.IUserService;
import com.mall.common.annotation.ExcelExport;
import com.mall.common.annotation.NoLogin;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.dto.PageDTO;
import com.mall.dto.UserDTO;
import com.mall.dto.condition.RoleConditionDTO;
import com.mall.dto.condition.UserConditionDTO;
import com.mall.entity.UserDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Tag(name = "用户管理", description = "用户管理相关操作")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 通过id查询用户信息
     *
     * @param id 系统ID
     * @return 用户信息
     */
    @NoLogin
//    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "通过id查询用户信息", description = "通过id查询用户信息")
    @GetMapping("/findById")
    public UserDTO findById(Long id) {
        return userService.findById(id);
    }

    /**
     * 根据条件查询用户列表
     *
     * @param userConditionDTO 用户条件DTO
     * @return 用户列表
     */
    @PostMapping("/searchByPage")
    public PageDTO<UserDO> searchByPage(@RequestBody UserConditionDTO userConditionDTO) {
        return userService.searchByPage(userConditionDTO);
    }

    /**
     * 添加用户
     *
     * @param userDTO 用户实体
     * @return 影响行数
     */
    @NoLogin
    @PostMapping("/insert")
    @Operation(summary = "添加用户", description = "添加一个用户")
    public int insert(@RequestBody UserDTO userDTO) {
        return userService.insert(userDTO);
    }

    /**
     * 修改用户
     *
     * @param userDTO 用户实体
     * @return 影响行数
     */
    @Operation(summary = "更新用户信息", description = "根据传入UserEntity更新原来的用户信息")
    @PostMapping("/update")
    public int update(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    /**
     * 删除用户
     *
     * @param ids 用户ID
     * @return 影响行数
     */
    @Operation(summary = "根据用户Id删除指定用户")
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody@NotNull List<Long> ids) {
        return userService.deleteByIds(ids);
    }


    /**
     * 重置密码
     *
     * @param ids 用户ID
     * @return 影响行数
     */
    @Operation(summary = "重置密码", description = "重置密码")
    @PostMapping("/resetPwd")
    public int resetPwd(@RequestBody@NotNull List<Long> ids) {
        return userService.resetPwd(ids);
    }

    /**
     * 导出角色数据
     *
     * @return 影响行数
     */
    @ExcelExport(ExcelBizTypeEnum.USER) // 通过注解实现excel导出
    @Operation(summary = "导出角色数据", description = "导出角色数据")
    @PostMapping("/export")
    public void export(@RequestBody RoleConditionDTO condition) {
    }
}
