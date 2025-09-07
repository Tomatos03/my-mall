package com.mall.web.controller.mall;

import com.mall.api.service.mall.IAttributeService;
import com.mall.dto.condition.mall.AttributeConditionDTO;
import com.mall.dto.mall.AttributeDTO;
import com.mall.dto.sys.PageDTO;
import com.mall.entity.mall.AttributeDO;
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
@RequestMapping("/")
public class AttributeController {

    @Autowired
    private IAttributeService attributeService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody AttributeDTO dto) {
        return attributeService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody AttributeDTO dto) {
        return attributeService.update(dto);
    }

    /**
     * 通过id查询属性信息
     *
     * @param id 系统ID
     * @return 属性信息
     */
    @Operation(summary = "通过id查询属性信息", description = "通过id查询属性信息")
    @GetMapping("/findById")
    public AttributeDTO findById(Long id) {
        return attributeService.findById(id);
    }

    /**
     * 根据条件查询属性列表
     *
     * @param condition 条件
     * @return 属性列表
     */
    @Operation(summary = "根据条件查询属性列表", description = "根据条件查询属性列表")
    @PostMapping("/searchByPage")
    public PageDTO<AttributeDO> searchByPage(@RequestBody AttributeConditionDTO condition) {
        return attributeService.searchByPage(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return attributeService.deleteByIds(ids);
    }

    @Operation(summary = "导出属性数据", description = "导出属性数据")
    @PostMapping("/export")
    public void export(@RequestBody AttributeConditionDTO condition) {
    }
}
