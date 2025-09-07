package com.mall.dto.condition.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Data
public class CommonConditionDTO extends PageConditionDTO {
    /**
     * 创建日期范围
     */
    @Schema(description = "创建日期范围")
    private List<String> betweenTime;

    /**
     * 创建开始时间
     */
    private String createBeginTime;

    /**
     * 创建结束时间
     */
    private String createEndTime;

    /**
     * 自定义excel表头列表
     */
    private List<String> customizeColumnNameList;

    /**
     * 查询条件
     */
    private String blurry;
}
