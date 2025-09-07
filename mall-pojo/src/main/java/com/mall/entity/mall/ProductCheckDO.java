package com.mall.entity.mall;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/7 15:56
 */
@Schema(description = "商品检查实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCheckDO {

    /**
     * 分类列表
     */
    private List<CategoryDO> categoryEntities;

    /**
     * 品牌列表
     */
    private List<BrandDO> brandEntities;
}
