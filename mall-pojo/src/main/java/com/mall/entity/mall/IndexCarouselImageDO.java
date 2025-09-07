package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 首页轮播图实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-08-21 18:34:11
 */
@Schema(description = "首页轮播图实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndexCarouselImageDO extends CommonDO {


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
}
