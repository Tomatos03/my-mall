package com.mall.web.controller.mall;

import com.mall.dto.mall.ProductPhotoDTO;
import com.mall.dto.condition.mall.ProductPhotoConditionDTO;
import com.mall.api.service.mall.IProductPhotoService;
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
@RequestMapping("/v1/productPhoto")
public class ProductPhotoController {

    @Autowired
    private IProductPhotoService productPhotoService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody ProductPhotoDTO dto) {
        return productPhotoService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody ProductPhotoDTO dto) {
        return productPhotoService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return productPhotoService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public List<ProductPhotoDTO> searchByCondition(@RequestBody ProductPhotoConditionDTO condition) {
        return productPhotoService.searchByCondition(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return productPhotoService.deleteByIds(ids);
    }
}
