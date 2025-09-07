package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商品图片实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-05-09 14:43:56
 */
@Schema(description = "商品图片实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductPhotoDO extends CommonDO {


	/**
	 * 商品ID
	 */
	@Schema(description = "商品ID")
	private Long productId;

	/**
	 * 图片名称
	 */
	@Schema(description = "图片名称")
	private String name;

	/**
	 * 图片url
	 */
	@Schema(description = "图片url")
	private String url;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

	/**
	 * 图片类型 1：封面 2：轮播图
	 */
	@Schema(description = "图片类型")
	private Integer type;
}
