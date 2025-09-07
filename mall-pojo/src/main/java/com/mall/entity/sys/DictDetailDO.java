package com.mall.entity.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "字典详情实体")
public class DictDetailDO extends CommonDO {
    /**
     * 数据字典id
     */
    @Schema(description = "数据字典id")
    private Long dictId;

    /**
     * 值
     */
    @Schema(description = "值")
    private String value;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 文本
     */
    @Schema(description = "文本")
    private String label;

    /**
     * 数据字典
     */
    @Schema(description = "数据字典")
    private DictDO dict;
}
