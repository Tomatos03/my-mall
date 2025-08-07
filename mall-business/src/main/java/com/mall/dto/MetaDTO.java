package com.mall.dto;

import lombok.Data;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Data
public class MetaDTO {

    /**
     * icon
     */
    private String icon;

    /**
     * 是否不缓存
     */
    private Boolean noCache;

    /**
     * 菜单标题
     */
    private String title;
}
