package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import lombok.Data;
import java.util.List;

/**
 * 首页公告查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-10-03 15:58:40
 */
@Data
public class IndexNoticeConditionDTO extends CommonConditionDTO {
   /**
    * ID集合
    */
    private List<Long> idList;
	/**
	 *  ID
     */
	private Long id;
	/**
	 *  标题
     */
	private String title;
	/**
	 *  内容
     */
	private String content;
	/**
	 *  排序
     */
	private Integer sort;
	/**
	 *  创建人ID
     */
	private Long createUserId;
	/**
	 *  创建人名称
     */
	private String createUserName;
	/**
	 *  修改人ID
     */
	private Long updateUserId;
	/**
	 *  修改人名称
     */
	private String updateUserName;
	/**
	 *  是否删除 1：已删除 0：未删除
     */
	private Integer isDel;
}
