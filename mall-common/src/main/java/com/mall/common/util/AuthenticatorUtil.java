package com.mall.common.util;

import com.mall.common.context.SpringContextHolder;
import com.mall.common.domain.security.AuthenticationUser;
import com.mall.constant.JobUserConst;
import com.mall.dto.AuthenticatedUserDTO;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public final class AuthenticatorUtil {
    private AuthenticatorUtil() {}

    private static AuthenticationManager authenticationManager() {
        return SpringContextHolder.getBean(AuthenticationManager.class);
    }

    public static Authentication authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken loginUser =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication auth = authenticationManager().authenticate(loginUser);

        SecurityContextHolder.getContext()
                             .setAuthentication(auth);
        return auth;
    }

    /**
     * 恢复授权
     *
     * @param authenticatedUser 已授权对象
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/6 23:49
     */
    public static void restoreAuthentication(AuthenticatedUserDTO authenticatedUser) {
        List<SimpleGrantedAuthority> roles = authenticatedUser.getRoles()
                                                              .stream()
                                                              .map(SimpleGrantedAuthority::new)
                                                              .toList();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authenticatedUser, null, roles);

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static AuthenticatedUserDTO getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            throw new AuthenticationCredentialsNotFoundException("未授权");

        return (AuthenticatedUserDTO) auth.getPrincipal();
    }

    public static AuthenticatedUserDTO convertFrom(Authentication auth) {
        AuthenticationUser user = (AuthenticationUser) auth.getPrincipal();

        List<String> roles = user.getAuthorities()
                                 .stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .toList();

        return AuthenticatedUserDTO.builder()
                                   .id(user.getId())
                                   .username(user.getUsername())
                                   .roles(roles)
                                   .build();
    }

    public static void setMockAuthenticatedUser() {
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        authenticatedUserDTO.setUsername(JobUserConst.DEFAULT_USER_NAME);
        authenticatedUserDTO.setId(JobUserConst.DEFAULT_USER_ID);

        UsernamePasswordAuthenticationToken mockUser = new UsernamePasswordAuthenticationToken(authenticatedUserDTO, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(mockUser);
    }

    public static void clearMockAuthenticatedUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
