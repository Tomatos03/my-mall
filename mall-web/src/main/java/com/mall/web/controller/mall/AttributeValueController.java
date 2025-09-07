package com.mall.web.controller.mall;

import com.mall.dto.mall.AttributeValueDTO;
import com.mall.dto.condition.mall.AttributeValueConditionDTO;
import com.mall.api.service.mall.IAttributeValueService;
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
@RequestMapping("/v1/attributeValue")
public class AttributeValueController {

    @Autowired
    private IAttributeValueService attributeValueService;

    /**
     * 新增
     */
    @PostMapping("/insert")
    public int insert(@RequestBody AttributeValueDTO dto) {
        return attributeValueService.insert(dto);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public int update(@RequestBody AttributeValueDTO dto) {
        return attributeValueService.update(dto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Long id) {
        return attributeValueService.delete(id);
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public List<AttributeValueDTO> searchByCondition(@RequestBody AttributeValueConditionDTO condition) {
        return attributeValueService.searchByCondition(condition);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody List<Long> ids) {
        return attributeValueService.deleteByIds(ids);
    }
}
