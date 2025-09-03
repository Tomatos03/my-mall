package com.mall.dto;

import com.mall.entity.DictDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "字典详情传输实体")
public class DictDetailDTO {
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
