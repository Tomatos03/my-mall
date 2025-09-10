package com.mall.web.controller.web;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */

import com.mall.api.service.mall.IProductService;
import com.mall.dto.condition.mall.ProductConditionDTO;
import com.mall.dto.sys.PageDTO;
import com.mall.entity.mall.ProductDO;
import com.mall.security.annotation.NoLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "web商品操作", description = "web商品操作")
@RestController
@RequestMapping("/v1/web/product")
public class WebProductController {

    @Autowired
    private IProductService productService;

    /**
     * 根据条件搜索商品列表
     *
     * @param condition 条件
     * @return 商品列表
     */
    @NoLogin
    @Operation(summary = "根据条件搜索商品列表", description = "根据条件搜索商品列表")
    @PostMapping("/search")
    public PageDTO<ProductDO> search(@RequestBody ProductConditionDTO condition) {
        return productService.searchFromEs(condition);
    }
}