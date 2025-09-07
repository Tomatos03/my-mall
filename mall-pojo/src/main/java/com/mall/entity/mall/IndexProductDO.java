package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * 首页商品实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-08-27 17:37:52
 */
@Schema(description = "首页商品实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndexProductDO extends CommonDO {


    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private Long productId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String model;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal price;

    /**
     * 封面
     */
    @Schema(description = "封面")
    private String cover;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 商品类型 1: 热门商品 2: 最新商品 3：秒杀商品
     */
    @Schema(description = "商品类型 1: 热门商品 2: 最新商品 3：秒杀商品")
    private Integer type;
}
