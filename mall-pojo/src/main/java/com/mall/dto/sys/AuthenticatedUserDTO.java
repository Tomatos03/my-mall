package com.mall.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUserDTO {
    private Long id;
    private String username;
    private String token;
    private List<String> roles;
}