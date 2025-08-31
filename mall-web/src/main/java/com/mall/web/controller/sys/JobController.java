package com.mall.web.controller.sys;

import com.mall.api.service.IJobService;
import com.mall.common.annotation.ExcelExport;
import com.mall.common.annotation.NoLogin;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.dto.JobDTO;
import com.mall.dto.PageDTO;
import com.mall.dto.condition.JobConditionDTO;
import com.mall.entity.JobDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/31
 */

@Tag(name = "部门管理", description = "部门管理相关操作")
@RestController
@RequestMapping("/v1/job")
public class JobController {
    @Autowired
    private IJobService jobService;

    @PostMapping("/searchByPage")
    @Operation(summary = "分页查询岗位", description = "分页查询岗位")
    public PageDTO<JobDO> searchByPage(@RequestBody JobConditionDTO conditionDTO) {
        return jobService.searchByPage(conditionDTO);
    }

    @NoLogin
    @Operation(summary = "删除岗位",  description = "删除岗位")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody @NotNull List<Long> ids) {
        return jobService.deleteByIds(ids);
    }

    @NoLogin
    @Operation(summary = "添加岗位",  description = "添加岗位")
    @PostMapping("/insert")
    public int insert(@RequestBody JobDTO dto) {
        return jobService.insert(dto);
    }


    @ExcelExport(ExcelBizTypeEnum.JOB)
    @NoLogin
    @Operation(summary = "导出岗位数据", description = "导出岗位数据")
    @PostMapping("/export")
    public void export(@RequestBody JobConditionDTO conditionDTO) {}
}
