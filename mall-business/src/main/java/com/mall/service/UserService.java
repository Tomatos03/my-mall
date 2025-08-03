package com.mall.service;

import cn.hutool.json.JSONUtil;
import com.mall.constant.TokenConst;
import com.mall.constant.UserConst;
import com.mall.entity.UserEntity;
import com.mall.entity.auth.AuthUserEntity;
import com.mall.entity.auth.TokenEntity;
import com.mall.mapper.UserMapper;
import com.mall.properties.JwtProperties;
import com.mall.util.JwtUtil;
import com.mall.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtProperties jwtProperties;
//    @Autowired
//    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 通过id查询用户信息
     *
     * @param id 系统ID
     * @return 用户信息
     */
    public UserEntity findById(Long id) {
        return userMapper.findById(id);
    }

//    /**
//     * 根据条件查询用户列表
//     *
//     * @param userConditionEntity 条件
//     * @return 用户列表
//     */
//    public ResponsePageEntity<UserEntity> searchByPage(UserConditionEntity userConditionEntity) {
//        int count = userMapper.searchCount(userConditionEntity);
//        if (count == 0) {
//            return ResponsePageEntity.buildEmpty(userConditionEntity);
//        }
//        List<UserEntity> userEntities = userMapper.searchByCondition(userConditionEntity);
//        return ResponsePageEntity.build(userConditionEntity, count, userEntities);
//    }


    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     * @return 影响行数
     */
    public int insert(UserEntity userEntity) {
        return userMapper.insert(userEntity);
    }

    /**
     * 修改用户
     *
     * @param userEntity 用户实体
     * @return 影响行数
     */
    public int update(UserEntity userEntity) {
        return userMapper.update(userEntity);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    public TokenEntity login(AuthUserEntity authUser) {
        String username = authUser.getUsername();
        log.info("尝试认证:{}", authUser);
        // 尝试进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, authUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 存储已经认证的用户的信息
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // 创建Jwt
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        String token = jwtUtil.createJwt(payload);

        // 缓存用户token
        redisUtil.set(
                TokenConst.PREFIX + username,
                token,
                jwtProperties.getExpiration(),
                TimeUnit.SECONDS
        );

        // 缓存用户Json
        authUser.setPassword("");
        String userJson = JSONUtil.toJsonStr(authUser);
        redisUtil.set(
                UserConst.PREFIX + username,
                userJson,
                jwtProperties.getExpiration(),
                TimeUnit.SECONDS
        );
        return new TokenEntity(username,  token);
    }


    public void logout(HttpServletRequest request) {
    }
}
