package com.mall.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Schema(description = "任务查询条件实体")
@Data
public class CommonTaskConditionDTO extends CommonConditionDTO {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

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
    @Schema(description = "任务类型 1：通用excel导出")
    private Integer type;

    /**
     * 执行状态 0：待执行 1：执行中 2：成功 3：失败
     */
    @Schema(description = "执行状态 0：待执行 1：执行中 2：成功 3：失败")
    private Integer status;

    /**
     * 执行状态集合
     */
    private List<Integer> statusList;

    /**
     * 失败次数
     */
    @Schema(description = "失败次数")
    private Integer failureCount;

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
    private LocalDate createTime;

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
    private LocalDate updateTime;

    /**
     * 是否删除 1：已删除 0：未删除
     */
    @Schema(description = "是否删除 1：已删除 0：未删除")
    private Integer isDel;

    /**
     * 业务类型 1：菜单 2：部门 3：角色 4：用户
     */
    @Schema(description = "业务类型 1：菜单 2：部门 3：角色 4：用户")
    private Integer bizType;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requestParam;
}
