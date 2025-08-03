package com.mall.service;

import com.mall.entity.WebUserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Component("myUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String ENCODED_PASSWORD = new BCryptPasswordEncoder().encode("123456");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new WebUserEntity(123L, username, ENCODED_PASSWORD, authorities);
    }
}
