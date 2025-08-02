package com.mall.config;

import com.mall.filter.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService defineUser() {
        UserDetails user = User.builder()
                                .username("root")
                                .password("{noop}zjlljz")
                                .roles("USER")
                                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 使用BCrypt加密算法加密用户密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // https://docs.spring.io/spring-security/reference/reactive/authorization/method.html#_customizing_authorization
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // 设置为空字符串，去除ROLE_前缀
    }

    @Bean
    public SecurityFilterChain filterChainConfig(HttpSecurity securityConfig) throws Exception {
        return securityConfig.csrf(AbstractHttpConfigurer::disable)
                             // 禁用 X-Frame-Options 允许页面被嵌入到 <iframe> 等标签中
                             .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                             .authorizeHttpRequests(auth -> {
                                 auth.requestMatchers(HttpMethod.GET,
                                                      "/*.html",
                                                      "/*/**.html",
                                                      "/*/**.css",
                                                      "/*/**.js",
                                                      "/webSocket/**"
                                     ).permitAll()
                                     .requestMatchers(
                                             "/doc.html",
                                             "/webjars/**",
                                             "/swagger-resources/**",
                                             "/v3/api-docs",
                                             "/v3/api-docs/**",
                                             "/swagger-ui.html",
                                             "/swagger-ui/**",
                                             "/knife4j/**"
                                     ).permitAll()
                                     .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                     .anyRequest().permitAll();
                             })
                             .formLogin(Customizer.withDefaults())
                             .sessionManagement(sessionConfig ->
                                     sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                             )
                             .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                             .exceptionHandling(exceptionHandler -> {
//                                 exceptionHandler.authenticationEntryPoint();
//                                 exceptionHandler.accessDeniedHandler();
                             })
                             .build();
    }
}