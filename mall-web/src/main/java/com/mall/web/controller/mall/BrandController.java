package com.mall.web.controller.mall;

import com.mall.dto.mall.BrandDTO;
import com.mall.dto.condition.mall.BrandConditionDTO;
import com.mall.api.service.mall.IBrandService;
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
@RequestMapping("/v1/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody BrandDTO dto) {
        return brandService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody BrandDTO dto) {
        return brandService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return brandService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public List<BrandDTO> searchByCondition(@RequestBody BrandConditionDTO condition) {
        return brandService.searchByCondition(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return brandService.deleteByIds(ids);
    }
}
