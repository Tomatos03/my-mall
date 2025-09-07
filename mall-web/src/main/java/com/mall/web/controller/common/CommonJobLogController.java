package com.mall.web.controller.common;

import com.mall.api.service.sys.ICommonJobLogService;
import com.mall.dto.sys.CommonJobLogDTO;
import com.mall.dto.condition.sys.CommonJobLogConditionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 *
 * @author Tomatos
 * @date 2025-09-05
 */
@RestController
@RequestMapping("/v1/commonJobLog")
public class CommonJobLogController {
    @Autowired
    private ICommonJobLogService commonJobLogService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody CommonJobLogDTO dto) {
        return commonJobLogService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody CommonJobLogDTO dto) {
        return commonJobLogService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return commonJobLogService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/query")
    public List<CommonJobLogDTO> query(@RequestBody CommonJobLogConditionDTO condition) {
        return commonJobLogService.searchByCondition(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return commonJobLogService.deleteByIds(ids);
    }
}
