package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-05-09 14:43:55
 */
@Schema(description = "分类实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDO extends CommonDO {

	/**
	 * 父分类ID
	 */
	@Schema(description = "父分类ID")
	private Long parentId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String name;

	/**
	 * 层级
	 */
	@Schema(description = "层级")
	private Integer level;

	/**
	 * 是否叶子节点
	 */
	@Schema(description = "是否叶子节点")
	private Integer isLeaf;
}
