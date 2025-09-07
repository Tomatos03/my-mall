package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户产品实体
 *
 * @author 苏三
 * @date 2024/9/4 下午2:52
 */
@Data
public class UserProductDO extends CommonDO {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;

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
    private String coverUrl;

    /**
     * 库存
     */
    private Integer stock;
}
