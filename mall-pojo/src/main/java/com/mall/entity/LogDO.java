package com.mall.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
@Schema(description = "日志实体")
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LogDO extends CommonDO {
    @Schema(description = "方法名称")
    private String methodName;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "请求ip")
    private String requestIp;

    @Schema(description = "浏览器")
    private String browser;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "请求参数")
    private String param;

    @Schema(description = "耗时，毫秒级")
    private Integer time;

    @Schema(description = "异常")
    private String exception;

    @Schema(description = "状态 1:成功 0:失败")
    private Integer status;
}
