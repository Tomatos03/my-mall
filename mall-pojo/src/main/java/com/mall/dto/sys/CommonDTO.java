package com.mall.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class CommonDTO {
    /**
     * 系统ID
     */
    private Long id;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long updateUserId;

    /**
     * 修改人名称
     */
    private String updateUserName;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDel;
}
