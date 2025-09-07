package com.mall.dto.sys;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "岗位传输实体")
public class JobDTO extends CommonDTO {
    @Schema(description = "岗位名称")
    private String name;

    @Schema(description = "岗位排序")
    private Integer sort;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "有效状态 1:有效 0:无效")
    private Boolean validStatus;
}
