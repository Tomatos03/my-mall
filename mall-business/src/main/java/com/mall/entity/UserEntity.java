package com.mall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@Data
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    /**
     * 用户名称
     */
    private String userName;
}
