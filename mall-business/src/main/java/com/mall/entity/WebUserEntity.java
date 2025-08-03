package com.mall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Schema(description = "Web用户")
public class WebUserEntity implements UserDetails {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    /**
     * 角色信息
     */
    private List<String> roles;

    public WebUserEntity(Long id,String username, String password, List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
