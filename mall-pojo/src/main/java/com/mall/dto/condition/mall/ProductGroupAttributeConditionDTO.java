package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import lombok.Data;
import java.util.List;

/**
 * 商品组属性查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-09-07 17:28:48
 */
@Data
public class ProductGroupAttributeConditionDTO extends CommonConditionDTO {

    /**
     * ID集合
     */
    private List<Long> idList;

    /**
     * ID
     */
    private Long id;
    /**
     * 商品组ID
     */
    private Long productGroupId;
    /**
     * 属性ID
     */
    private Long attributeId;
    /**
     * 属性值ID
     */
    private Long attributeValueId;
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
}
