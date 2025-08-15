package com.mall.controller.sys;

import com.mall.dto.DeptDTO;
import com.mall.entity.DeptDO;
import com.mall.entity.condition.DeptConditionDTO;
import com.mall.entity.condition.ResponsePage;
import com.mall.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    private DeptService deptService;

    @Operation(description = "查询部门树", summary = "查询部门树")
    @PostMapping("/searchByTree")
    public List<DeptDTO> searchByTree(@RequestBody DeptConditionDTO deptConditionDTO) {
        return deptService.searchByTree(deptConditionDTO);
    }

    @Operation(summary = "根据条件查询部门列表", description = "根据条件查询部门列表")
    @PostMapping("/searchByPage")
    public ResponsePage<DeptDO> searchByPage(@RequestBody DeptConditionDTO deptConditionDTO) {
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

    @Operation(summary = "导出部门数据", description = "导出部门数据")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeptConditionDTO deptConditionDTO) throws IOException {
        deptService.export(response, deptConditionDTO);
    }

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
