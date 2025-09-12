package com.mall.entity.mall;

import com.mall.entity.sys.CommonDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页公告实体 该项目是知识星球：java突击队 的内部项目
 *
 * @author 苏三
 * @date 2024-10-03 15:58:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndexNoticeDO extends CommonDO {

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	private String title;

	/**
	 * 内容
	 */
	@Schema(description = "内容")
	private String content;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;
}
