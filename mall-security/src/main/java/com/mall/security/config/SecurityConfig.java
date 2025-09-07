package com.mall.security.config;

import com.mall.security.annotation.NoLogin;
import com.mall.common.context.SpringBeanHolder;
import com.mall.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService defineUser(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                                .username("root")
                                .password(passwordEncoder.encode("zjlljz"))
                                .roles("USER")
                                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 使用BCrypt加密算法加密用户密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 当实现UserDetailsService接口时, 不会在自动注入AuthenticationManager实例对象, 需要手动注册
    @Bean
    public AuthenticationManager authenticationManager(@Qualifier("myUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    // https://docs.spring.io/spring-security/reference/reactive/authorization/method.html#_customizing_authorization
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // 设置为空字符串，去除ROLE_前缀
    }

    @Bean
    public SecurityFilterChain filterChainConfig(HttpSecurity securityConfig, JwtFilter jwtFilter) throws Exception {
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
                                     .requestMatchers(getWhiteList()).permitAll()
                                     .anyRequest().authenticated();
                             })
                             .formLogin(AbstractHttpConfigurer::disable)
                             .sessionManagement(sessionConfig ->
                                     sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                             )
                             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                             .exceptionHandling(exceptionHandler -> {
//                                 exceptionHandler.authenticationEntryPoint();
//                                 exceptionHandler.accessDeniedHandler();
                             })
                             .build();
    }

    private String[] getWhiteList() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = SpringBeanHolder.getBean(RequestMappingHandlerMapping.class)
                                                                                .getHandlerMethods();
        List<String> whiteList = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            NoLogin methodAnnotation = handlerMethod.getMethodAnnotation(NoLogin.class);
            if (methodAnnotation == null)
                continue;
            List<String> patterns = entry.getKey()
                                     .getPathPatternsCondition()
                                     .getPatterns()
                                     .stream()
                                     .map(PathPattern::toString)
                                     .toList();
            whiteList.addAll(patterns);
        }
        return whiteList.toArray(new String[0]);
    }
}