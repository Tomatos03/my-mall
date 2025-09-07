package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品属性查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-05-09 14:43:56
 */
@Schema(description = "商品属性查询条件实体")
@Data
public class ProductAttributeConditionDTO extends CommonConditionDTO {


	/**
	 *  ID
     */
	@Schema(description = "ID")
	private Long id;

	/**
	 *  商品ID
     */
	@Schema(description = "商品ID")
	private Long productId;

	/**
	 * 商品ID集合
	 */
	private List<Long> productIdList;

	/**
	 *  属性ID
     */
	@Schema(description = "属性ID")
	private Long attributeId;

	/**
	 *  属性值ID
     */
	@Schema(description = "属性值ID")
	private Long attributeValueId;

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
