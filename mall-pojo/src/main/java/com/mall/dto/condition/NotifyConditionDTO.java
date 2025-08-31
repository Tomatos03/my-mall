package com.mall.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */
@Data
@Builder
@Schema(description = "通知查询条件实体")
public class NotifyConditionDTO extends CommonConditionDTO {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 通知类型 1：excel导出
     */
    @Schema(description = "通知类型 1：excel导出")
    private Integer type;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 跳转地址
     */
    @Schema(description = "跳转地址")
    private String linkUrl;

    /**
     * 阅读状态 0：未阅读 1：已阅读
     */
    @Schema(description = "阅读状态 0：未阅读 1：已阅读")
    private Integer readStatus;

    /**
     * 需要推送通知的用户ID
     */
    @Schema(description = "需要推送通知的用户ID")
    private Long toUserId;

    /**
     * 是否已推送
     */
    @Schema(description = "是否已推送")
    private Integer isPush;

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
}
