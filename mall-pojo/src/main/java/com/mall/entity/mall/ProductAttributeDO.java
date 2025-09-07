package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品属性实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-05-09 14:43:56
 */
@Schema(description = "商品属性实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAttributeDO extends CommonDO {


	/**
	 * 商品ID
	 */
	@Schema(description = "商品ID")
	private Long productId;

	/**
	 * 属性ID
	 */
	@Schema(description = "属性ID")
	private Long attributeId;

	/**
	 * 属性值ID
	 */
	@Schema(description = "属性值ID")
	private Long attributeValueId;
}
