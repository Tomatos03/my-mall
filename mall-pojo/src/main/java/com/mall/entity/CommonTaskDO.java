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
 * @date : 2025/8/15
 */
@Schema(description = "定时任务实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommonTaskDO extends CommonDO {
    public static final int MAX_FAILURE_COUNT = 3;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String name;

    /**
     * 下载文件地址
     */
    @Schema(description = "下载文件地址")
    private String fileUrl;

    /**
     * 任务类型 1：通用excel导出
     */
    @Schema(description = "任务类型 1：通用excel导出 2：发邮件")
    private Integer type;

    /**
     * 执行状态 0：待执行 1：执行中 2：成功 3：失败
     */
    @Schema(description = "执行状态 0：待执行 1：执行中 2：成功 3：失败")
    private Integer status;

    /**
     * 失败次数
     */
    @Schema(description = "失败次数")
    private Integer failureCount;

    /**
     * 业务类型
     * 任务类型时通用excel导出时
     * 1：菜单 2：部门 3：角色 4：用户
     * <p>
     * 任务类型时发邮件时
     * 1：异地登录
     */
    @Schema(description = "业务类型 1：菜单 2：部门 3：角色 4：用户")
    private Integer bizType;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requestParam;
}
