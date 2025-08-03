package com.mall.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author : Tomatos
 * @date : 2025/8/3
 */
@Component
public class PasswordUtil {

    @Value("${mall.mgt.password.privateKey:MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAv5YziQe5zP5OAyVYfIh2fJDiJuEeD4ZGNpVQjiPvsNGhvwjF/SCDECMwZl1lYAL6wNDNNXhr/ILV5xF0ZHg0+wIDAQABAkBDQRjqO5oPyh3coNmFLepaJAofl9sTnBE9zACxRRA5Q2ErwtUVi+A4qLX+HPDbPFWCbvEPKsGods4BiAL+rxWxAiEA+W9p3bfu+JA2XTxEboH9Cd5/g92PZGALne+cVKAKfFcCIQDEoQebpFK/hw8AWvWRZ4vXZLoocv8qEhnPEySCqTtl/QIhAK0vdSLTdaGxh31+vci4ijcS/BhTeh7oLMiwuCttnzorAiEAouivvvokPN8PRMIX10KtD+Y6fizYz+hzTaeUhlTkC5kCIQCPDDmumEPGzUDmFe1hlL3AqUeCNNnVhxYQrI4bNHFZvQ==}")
    private String privateKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private PasswordUtil() {}


    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 解密RSA密码
     *
     * @param encodePassword 私钥加密的密码
     * @return 解密后的密码
     */
    public String decodeRsaPassword(String encodePassword) {
        RSA rsa = new RSA(privateKey, null);
        return new String(rsa.decrypt(encodePassword, KeyType.PrivateKey));
    }
}
