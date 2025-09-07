package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import com.mall.entity.mall.ProductDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-05-09 14:43:56
 */
@Schema(description = "商品查询条件实体")
@Data
public class ProductConditionDTO extends CommonConditionDTO {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * ID集合
     */
    @Schema(description = "ID集合")
    private List<Long> idList;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 品牌ID
     */
    @Schema(description = "品牌ID")
    private Long brandId;

    /**
     * 单位ID
     */
    @Schema(description = "单位ID")
    private Long unitId;

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
     * hash值
     */
    @Schema(description = "hash值")
    private String hash;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer quantity;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal price;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 创建人名称
     */
    @Schema(description = "创建人名称")
    private String createUserName;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateUserId;

    /**
     * 修改人名称
     */
    @Schema(description = "修改人名称")
    private String updateUserName;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 是否删除 1：已删除 0：未删除
     */
    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;

    /**
     * 关键字
     */
    @Schema(description = "关键字")
    private String keyword;

    /**
     * 商品查询条件
     */
    @Schema(description = "商品查询条件")
    private List<ProductDO> productEntities;

    /**
     * 商品组ID
     */
    @Schema(description = "商品组ID")
    private Long productGroupId;

    /**
     * 商品组ID集合
     */
    @Schema(description = "商品组ID集合")
    private List<Long> productGroupIdList;

    /**
     * 逻辑删除ID，默认是0，表示未删除
     */
    private Long delId;
}
