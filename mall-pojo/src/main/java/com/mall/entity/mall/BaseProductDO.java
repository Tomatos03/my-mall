package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import lombok.Data;

/**
 * 商品基本信息
 *
 * @author 苏三
 * @date 2024/9/8 下午1:37
 */
@Data
public class BaseProductDO extends CommonDO {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 单位ID
     */
    private Long unitId;

    /**
     * 单位名称
     */
    private String unitName;
}
