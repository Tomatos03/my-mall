package com.mall.web.controller.mall;

import com.mall.api.service.mall.IProductService;
import com.mall.dto.condition.mall.ProductConditionDTO;
import com.mall.dto.mall.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody ProductDTO dto) {
        return productService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody ProductDTO dto) {
        return productService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public List<ProductDTO> searchByCondition(@RequestBody ProductConditionDTO condition) {
        return productService.searchByCondition(condition);
    }


    /**
     * 批量创建商品
     *
     * @param productDTOList 批量创建商品
     * @return 影响行数
     */
    @Operation(summary = "批量创建商品", description = "批量创建商品")
    @PostMapping("/generate")
    public List<ProductDTO> generate(@RequestBody List<ProductDTO> productDTOList) {
        return productService.generate(productDTOList);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return productService.deleteByIds(ids);
    }
}
