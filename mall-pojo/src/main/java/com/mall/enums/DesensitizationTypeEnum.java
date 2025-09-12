package com.mall.enums;

import cn.hutool.core.util.DesensitizedUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/12
 */
@Getter
@AllArgsConstructor
public enum DesensitizationTypeEnum {
    MOBILE("mobile", "手机号") {
        @Override
        public String maskSensitiveData(String data) {
            return DesensitizedUtil.mobilePhone(data);
        }
    },
    IDENTIFY("identify", "身份证号") {
        @Override
        public String maskSensitiveData(String data) {
            return DesensitizedUtil.idCardNum(data, 3, 4);
        }
    },
    BANKCARD("bankcard", "银行卡号") {
        @Override
        public String maskSensitiveData(String data) {
            return DesensitizedUtil.bankCard(data);
        }
    },

    EMAIL("email", "邮箱") {
        @Override
        public String maskSensitiveData(String data) {
            return DesensitizedUtil.email(data);
        }
    },
    DEFAULT("default", "默认"),
    CUSTOM("custom", "自定义");

    private final String type;

    private final String desc;


    /**
     * 遮挡敏感数据
     *
     * @param data
     * @return
     */
    public String maskSensitiveData(String data) {
        return data;
    }

    public String maskSensitiveData(String data, Function<String, String> maskFunction) {
        return maskFunction.apply(data);
    }
}
