package com.mall.security.service;

import com.mall.api.service.IRoleService;
import com.mall.api.service.IUserService;
import com.mall.entity.UserDO;
import com.mall.common.domain.security.AuthenticationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Component("myUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public AuthenticationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userService.findByUserName(username);
        if (userDO == null)
            return null;

        List<SimpleGrantedAuthority> userRoles = getUserRoles(userDO.getId());
        return AuthenticationUser.builder()
                                 .id(userDO.getId())
                                 .username(username)
                                 .password(userDO.getPassword())
                                 .authorities(userRoles)
                                 .build();
    }

    private List<SimpleGrantedAuthority> getUserRoles(Long userId) {
        return roleService.findRoleByUserId(userId)
                          .stream()
                          .map(roleDO -> new SimpleGrantedAuthority(roleDO.getPermission()))
                          .toList();
    }
}
