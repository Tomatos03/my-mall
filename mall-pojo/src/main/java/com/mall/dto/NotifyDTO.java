package com.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */
@Schema(description = "消息传输对象")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDTO extends CommonDTO {
    @Schema(description = "通知类型 1：excel导出")
    private Integer type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "跳转地址")
    private String linkUrl;

    @Schema(description = "阅读状态 0：未阅读 1：已阅读")
    private Integer readStatus;

    @Schema(description = "推送目标用户ID")
    private Long toUserId;
}
