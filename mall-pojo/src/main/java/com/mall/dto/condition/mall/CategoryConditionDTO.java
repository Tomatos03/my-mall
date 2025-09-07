package com.mall.dto.condition.mall;

import com.mall.dto.condition.sys.CommonConditionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类查询条件实体
 *
 * @author 苏三 该项目是知识星球：java突击队 的内部项目
 * @date 2024-05-09 14:43:55
 */
@Schema(description = "分类查询条件实体")
@Data
public class CategoryConditionDTO extends CommonConditionDTO {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * ID集合
     */
    @Schema(description = "ID集合")
    private List<Long> idList;

    /**
     * 父分类ID
     */
    @Schema(description = "父分类ID")
    private Long parentId;

    /**
     * 定时任务名称
     */
    @Schema(description = "定时任务名称")
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

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 创建人名称
     */
    @Schema(description = "创建人名称")
    private String createUserName;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateUserId;

    /**
     * 修改人名称
     */
    @Schema(description = "修改人名称")
    private String updateUserName;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 是否删除 1：已删除 0：未删除
     */
    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;

    /**
     * 是否查询整课分类树
     */
    @Schema(description = "是否查询整课分类树")
    private Boolean queryTree;
}
