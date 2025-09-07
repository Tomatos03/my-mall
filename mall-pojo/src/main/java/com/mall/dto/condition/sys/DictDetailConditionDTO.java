package com.mall.dto.condition.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@Schema(description = "部门查询条件DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictDetailConditionDTO extends CommonConditionDTO {
    @Schema(description = "数据字典名称")
    private String dictName;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "数据字典id")
    private Long dictId;

    @Schema(description = "数据字典id集合")
    private List<Long> dictIdList;

    @Schema(description = "值")
    private String value;

    @Schema(description = "文本")
    private String label;

    @Schema(description = "创建人ID")
    private Long createUserId;

    @Schema(description = "创建人名称")
    private String createUserName;

    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    @Schema(description = "修改人ID")
    private Long updateUserId;

    @Schema(description = "修改人名称")
    private String updateUserName;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;
}
