package com.mall.controller;

import com.mall.entity.UserEntity;
import com.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过id查询用户信息
     *
     * @param id 系统ID
     * @return 用户信息
     */
//    @NoLogin
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "通过id查询用户信息", description = "通过id查询用户信息")
    @GetMapping("/findById")
    public UserEntity findById(Long id) {
        return userService.findById(id);
    }

    /**
     * 根据条件查询用户列表
     *
     * @param userConditionEntity 条件
     * @return 用户列表
     */
//    @PostMapping("/searchByPage")
//    public ResponsePageEntity<UserEntity> searchByPage(@RequestBody UserConditionEntity userConditionEntity) {
//        return userService.searchByPage(userConditionEntity);
//    }


    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     * @return 影响行数
     */
    @PostMapping("/insert")
    @Operation(summary = "添加用户", description = "添加一个用户")
    public int insert(@RequestBody UserEntity userEntity) {
        return userService.insert(userEntity);
    }

    /**
     * 修改用户
     *
     * @param userEntity 用户实体
     * @return 影响行数
     */
    @Operation(summary = "更新用户信息", description = "根据传入UserEntity更新原来的用户信息")
    @PostMapping("/update")
    public int update(@RequestBody UserEntity userEntity) {
        return userService.update(userEntity);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    @Operation(summary = "根据用户Id删除指定用户")
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody @NotNull Long id) {
        return userService.deleteById(id);
    }
}
