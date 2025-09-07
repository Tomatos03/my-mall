package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 属性值实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-05-09 14:43:55
 */
@Schema(description = "属性值实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttributeValueDO extends CommonDO {
    /**
     * 属性ID
     */
    @Schema(description = "属性ID")
    private Long attributeId;

    /**
     * 属性名称
     */
    @Schema(description = "属性名称")
    private String attributeName;

    /**
     * 属性值
     */
    @Schema(description = "属性值")
    private String value;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
}
