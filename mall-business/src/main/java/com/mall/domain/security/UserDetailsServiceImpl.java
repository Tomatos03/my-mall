package com.mall.domain.security;

import com.mall.entity.UserDO;
import com.mall.mapper.RoleMapper;
import com.mall.mapper.UserMapper;
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
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public AuthenticationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userMapper.findByUserName(username);
        if (userDO == null)
            return null;

        List<SimpleGrantedAuthority> userRoles = getUserRoles(userDO.getId());
        return AuthenticationUser.builder()
                                 .username(username)
                                 .password(userDO.getPassword())
                                 .authorities(userRoles)
                                 .build();
    }

    private List<SimpleGrantedAuthority> getUserRoles(Long userId) {
        return roleMapper.findRoleByUserId(userId)
                         .stream()
                         .map(roleDO -> new SimpleGrantedAuthority(roleDO.getPermission()))
                         .toList();
    }
}
