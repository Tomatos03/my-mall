package com.mall.domain.security;

import com.mall.dto.AuthenticatedUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/5
 */
@Component
public class Authenticator {
    @Autowired
    AuthenticationManager authenticationManager;

    public Authentication authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticated = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return authenticated;
    }

    /**
     * 恢复授权
     *
     * @param authenticatedUserDTO 已授权对象
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/6 23:49
     */
    public void restoreAuthentication(AuthenticatedUserDTO authenticatedUserDTO) {
        List<SimpleGrantedAuthority> roles = authenticatedUserDTO.getRoles()
                                                                 .stream()
                                                                 .map(SimpleGrantedAuthority::new)
                                                                 .toList();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticatedUserDTO,
                                                        null,
                                                        roles);
        SecurityContextHolder.getContext()
                             .setAuthentication(authenticationToken);
    }

    public AuthenticatedUserDTO getAuthenticatedUser() {
        return (AuthenticatedUserDTO) SecurityContextHolder.getContext()
                                                           .getAuthentication()
                                                           .getPrincipal();
    }

    public static AuthenticatedUserDTO convertFrom(Authentication authentication) {
        AuthenticationUser authenticationUser = (AuthenticationUser) authentication.getPrincipal();

        List<String> roleList = authenticationUser.getAuthorities()
                                                  .stream()
                                                  .map(GrantedAuthority::getAuthority)
                                                  .toList();

        return AuthenticatedUserDTO.builder()
                                   .username(authenticationUser.getUsername())
                                   .roles(roleList)
                                   .build();
    }
}