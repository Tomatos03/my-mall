package com.mall.entity.mall;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-05-09 14:43:56
 */
@Schema(description = "商品实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDO extends BaseProductDO {

    /**
     * 商品组ID
     */
    @Schema(description = "商品组ID")
    private Long productGroupId;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 品牌ID
     */
    @Schema(description = "品牌ID")
    private Long brandId;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 单位ID
     */
    @Schema(description = "单位ID")
    private Long unitId;

    /**
     * 单位名称
     */
    @Schema(description = "单位名称")
    private String unitName;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String name;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String model;

    /**
     * 规格hash值
     */
    @Schema(description = "规格hash值")
    private String hash;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer quantity;

    /**
     * 剩余库存
     */
    @Schema(description = "剩余库存")
    private Integer remainQuantity;

    /**
     * 库存
     */
    @Schema(description = "库存")
    private Integer stock;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal price;

    /**
     * 封面图片url
     */
    @Schema(description = "封面图片url")
    private String coverUrl;

    /**
     * 商品组属性集合
     */
    @Schema(description = "商品组集合")
    private List<AttributeValueDO> spuAttributeEntityList;

    /**
     * 商品属性集合
     */
    @Schema(description = "商品属性集合")
    private List<AttributeValueDO> skuAttributeEntityList;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private List<ProductPhotoDO> productPhotoEntityList;

    /**
     * 是否新创建的商品
     */
    private Boolean isNew;

    /**
     * 属性值组合
     */
    private String attributeValueIds;

    /**
     * 封面图片
     */
    private List<String> cover;

    /**
     * 轮播图
     */
    private List<String> swiper;

    /**
     * 详情
     */
    private String detail;

    /**
     * 商品组实体
     */
    private ProductGroupDO productGroup;

    /**
     * 逻辑删除ID，默认是0，表示未删除
     */
    private Long delId;
}
