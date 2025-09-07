package com.mall.web.controller.mall;

import com.mall.dto.mall.UnitDTO;
import com.mall.dto.condition.mall.UnitConditionDTO;
import com.mall.api.service.mall.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 控制层
 *
 * @author Tomatos
 * @date 2025-09-07
 */
@RestController
@RequestMapping("/v1/unit")
public class UnitController {

    @Autowired
    private IUnitService unitService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody UnitDTO dto) {
        return unitService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody UnitDTO dto) {
        return unitService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return unitService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public List<UnitDTO> searchByCondition(@RequestBody UnitConditionDTO condition) {
        return unitService.searchByCondition(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return unitService.deleteByIds(ids);
    }
}
