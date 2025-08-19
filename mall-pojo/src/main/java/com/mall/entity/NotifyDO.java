package com.mall.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */
@Data
@SuperBuilder
@Schema(description = "通知实体")
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDO extends BaseDO {
    @Schema(description = "通知类型（1：excel导出）")
    private Integer type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "跳转地址")
    private String linkUrl;

    @Schema(description = "阅读状态（0：未阅读 1：已阅读）")
    private Integer readStatus;

    @Schema(description = "推送状态（0:未推送, 1:已推送）")
    private Integer isPush;

    @Schema(description = "推送目标用户ID")
    private Long toUserId;
}
