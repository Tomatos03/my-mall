package com.mall.dto.sys;

import lombok.Builder;
import lombok.Data;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Builder
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
