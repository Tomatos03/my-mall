package com.mall.entity.sys;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
@Schema(description = "部门实体")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeptDO extends CommonDO {
    /**
     * 名称
     */
    @ExcelProperty(value = "部门名称", index = 0)
    @Schema(description = "名称")
    private String name;

    /**
     * 上级部门
     */
    @ExcelProperty(value = "上级部门ID", index = 1)
    @Schema(description = "上级部门")
    private Long pid;

    /**
     * 有效状态 1:有效 0:无效
     */
    @ExcelProperty(value = "有效状态", index = 2)
    @Schema(description = "有效状态 1:有效 0:无效")
    private Boolean validStatus;

    /**
     * 角色ID
     */
    private Long roleId;
}
