package com.mall.common.domain.convert;

import cn.hutool.core.bean.BeanUtil;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
public final class Convert {
    private Convert() {};

    public static <DO, DTO> DO convertDO(DTO dto, Class<DO> doClass) {
        return BeanUtil.copyProperties(dto, doClass);
    }
}
