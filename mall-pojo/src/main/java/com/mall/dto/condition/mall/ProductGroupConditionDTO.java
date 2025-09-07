package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import com.mall.entity.mall.ProductGroupDO;
import lombok.Data;

import java.util.List;

/**
 * 商品组查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-09-07 17:28:47
 */
@Data
public class ProductGroupConditionDTO extends CommonConditionDTO {

    /**
     * ID集合
     */
    private List<Long> idList;

    /**
     * ID
     */
    private Long id;
    /**
     * 分类ID
     */
    private Long categoryId;


    /**
     * 单位ID
     */
    private Long unitId;

    /**
     * 商品组名称
     */
    private Long name;

    /**
     * 规格
     */
    private String model;

    /**
     * hash值
     */
    private String hash;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 创建人名称
     */
    private String createUserName;
    /**
     * 修改人ID
     */
    private Long updateUserId;
    /**
     * 修改人名称
     */
    private String updateUserName;
    /**
     * 是否删除 1：已删除 0：未删除
     */
    private Integer isDel;

    /**
     * 商品组查询条件
     */
    private List<ProductGroupDO> productGroupEntities;

    /**
     * 逻辑删除ID，默认是0，表示未删除
     */
    private Long delId;
}
