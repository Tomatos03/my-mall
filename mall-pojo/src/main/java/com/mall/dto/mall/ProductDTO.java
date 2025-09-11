package com.mall.dto.mall;

import com.mall.annotation.SensitiveWord;
import com.mall.dto.sys.CommonDTO;
import com.mall.entity.mall.AttributeValueDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO extends CommonDTO {
 
     /**
      * 分类ID
      */
     private Long categoryId;

     /**
      * 品牌ID
      */
     private Long brandId;

     /**
      * 单位ID
      */
     private Long unitId;

     /**
      * 商品名称
      */
     @SensitiveWord
     private String name;

     /**
      * 规格
      */
     @SensitiveWord
     private String model;

     /**
      * 数量
      */
     private Integer quantity;

     /**
      * 价格
      */
     private BigDecimal price;

     /**
      * 销售状态 1：上架 0：下架
      */
     private Integer saleStatus;

     /**
      * 是否热销商品 1：是 0：否
      */
     private Integer isHot;

     /**
      * 是否推荐商品 1：是 0：否
      */
     private Integer isRecommend;

    /**
     * 商品组属性集合
     */
    @Schema(description = "商品组集合")
    private List<AttributeValueDO> spuAttributeEntityList; // SPU (Standard Product Unit)

    /**
     * 商品属性集合
     */
    @Schema(description = "商品属性集合")
    private List<AttributeValueDO> skuAttributeEntityList; // SKU (Stock Keeping Unit)
}