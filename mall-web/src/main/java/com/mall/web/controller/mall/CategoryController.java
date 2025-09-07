package com.mall.web.controller.mall;

import com.mall.dto.mall.CategoryDTO;
import com.mall.dto.condition.mall.CategoryConditionDTO;
import com.mall.api.service.mall.ICategoryService;
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
@RequestMapping("/v1/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody CategoryDTO dto) {
        return categoryService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody CategoryDTO dto) {
        return categoryService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public List<CategoryDTO> searchByCondition(@RequestBody CategoryConditionDTO condition) {
        return categoryService.searchByCondition(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return categoryService.deleteByIds(ids);
    }
}
