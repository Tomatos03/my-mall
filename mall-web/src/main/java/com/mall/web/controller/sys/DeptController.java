package com.mall.web.controller.sys;

import com.mall.api.service.IDeptService;
import com.mall.common.annotation.ExcelExport;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.dto.DeptDTO;
import com.mall.dto.PageDTO;
import com.mall.entity.DeptDO;
import com.mall.dto.condition.DeptConditionDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
@RestController
@RequestMapping("/v1/dept")
public class DeptController {
    @Autowired
    private IDeptService deptService;

    @Operation(description = "查询部门树", summary = "查询部门树")
    @PostMapping("/searchByTree")
    public List<DeptDTO> searchByTree(@RequestBody DeptConditionDTO deptConditionDTO) {
        return deptService.searchByTree(deptConditionDTO);
    }

    @Operation(summary = "根据条件查询部门列表", description = "根据条件查询部门列表")
    @PostMapping("/searchByPage")
    public PageDTO<DeptDO> searchByPage(@RequestBody DeptConditionDTO deptConditionDTO) {
        return deptService.searchByPage(deptConditionDTO);
    }

    /**
     * 批量删除菜单
     *
     * @param ids 菜单ID
     * @return 影响行数
     */
    @Operation(summary = "删除部门", description = "删除部门")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody @NotNull List<Long> ids) {
        return deptService.deleteByIds(ids);
    }

    // 通过@ExcelExport注解异步处理excel导出
    @ExcelExport(ExcelBizTypeEnum.DEPT)
    @Operation(summary = "导出部门数据", description = "导出部门数据")
    @PostMapping("/export")
    public void export() {}

    @Operation(summary = "插入部门", description = "插入部门")
    @PostMapping("/insert")
    public int insert(@RequestBody DeptDTO deptDTO) {
        return deptService.insert(deptDTO);
    }

    @Operation(summary = "更新部门", description = "更新部门")
    @PostMapping("/update")
    public int update(@RequestBody DeptDTO deptDTO) {
        return deptService.update(deptDTO);
    }
}
