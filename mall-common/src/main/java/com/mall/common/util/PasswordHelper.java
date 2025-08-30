package com.mall.common.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.mall.common.context.SpringContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
public final class PasswordHelper {
    private PasswordHelper(){};

    public static PasswordEncoder passwordEncoder() {
        return SpringContextHolder.getBean(PasswordEncoder.class);
    }

    public static String encode(String password) {
        return passwordEncoder().encode(password);
    }

    /**
     * 解密RSA密码
     *
     * @param encodePassword 私钥加密的密码
     * @return 解密后的密码
     */
    public static String decodeRsaPassword(String encodePassword, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return new String(rsa.decrypt(encodePassword, KeyType.PrivateKey));
    }
}
