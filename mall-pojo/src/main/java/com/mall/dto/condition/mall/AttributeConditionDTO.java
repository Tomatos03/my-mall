package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 属性查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-05-09 14:43:55
 */
@Schema(description = "属性查询条件实体")
@Data
public class AttributeConditionDTO extends CommonConditionDTO {


	/**
	 *  ID
     */
	@Schema(description = "ID")
	private Long id;

	/**
	 * ID集合
	 */
	@Schema(description = "ID集合")
	private List<Long> idList;

	/**
	 *  属性名称
     */
	@Schema(description = "属性名称")
	private String name;

	/**
	 *  创建人ID
     */
	@Schema(description = "创建人ID")
	private Long createUserId;

	/**
	 *  创建人名称
     */
	@Schema(description = "创建人名称")
	private String createUserName;

	/**
	 *  创建日期
     */
	@Schema(description = "创建日期")
	private LocalDateTime createTime;

	/**
	 *  修改人ID
     */
	@Schema(description = "修改人ID")
	private Long updateUserId;

	/**
	 *  修改人名称
     */
	@Schema(description = "修改人名称")
	private String updateUserName;

	/**
	 *  修改时间
     */
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	/**
	 *  是否删除 1：已删除 0：未删除
     */
	@Schema(description = "是否删除 1：已删除 0：未删除")
	private Integer isDel;
}
