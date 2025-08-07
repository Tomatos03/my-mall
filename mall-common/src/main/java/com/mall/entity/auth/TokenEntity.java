package com.mall.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenEntity {
    /**
     * 用户名称
     */
    private String username;

    /**
     * token
     */
    private String token;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 过期时间
     */
    private int expiresIn;

    public TokenEntity(String username, String token, List<String> roles) {
        this.roles = roles;
        this.token = token;
        this.username = username;
    }
}
