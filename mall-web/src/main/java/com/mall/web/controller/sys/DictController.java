package com.mall.web.controller.sys;

import com.mall.api.service.sys.IDictService;
import com.mall.dto.sys.DictDTO;
import com.mall.dto.sys.PageDTO;
import com.mall.dto.condition.sys.DictConditionDTO;
import com.mall.entity.sys.DictDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 *
 * @author Tomatos
 * @date 2025-09-03
 */
@Tag(name = "数据字典操作", description = "数据字典接口")
@RestController
@RequestMapping("/v1/dict")
public class DictController {

    @Autowired
    private IDictService dictService;

    /**
     * 通过id查询数据字典信息
     *
     * @param id 系统ID
     * @return 数据字典信息
     */
    @Operation(summary = "通过id查询数据字典信息", description = "通过id查询数据字典信息")
    @GetMapping("/findById")
    public DictDTO findById(Long id) {
        return dictService.findById(id);
    }

    /**
     * 根据条件查询数据字典列表
     *
     * @param condition 条件
     * @return 数据字典列表
     */
    @Operation(summary = "根据条件查询数据字典列表", description = "根据条件查询数据字典列表")
    @PostMapping("/searchByPage")
    public PageDTO<DictDO> searchByPage(@RequestBody DictConditionDTO condition) {
        return dictService.searchByPage(condition);
    }

    /**
     * 添加数据字典
     *
     * @param dictDO 数据字典实体
     * @return 影响行数
     */
    @Operation(summary = "添加数据字典", description = "添加数据字典")
    @PostMapping("/insert")
    public int insert(@RequestBody DictDTO dictDO) {
        return dictService.insert(dictDO);
    }

    /**
     * 修改数据字典
     *
     * @param DictDTO 数据字典实体
     * @return 影响行数
     */
    @Operation(summary = "修改数据字典", description = "修改数据字典")
    @PostMapping("/update")
    public int update(@RequestBody DictDTO DictDTO) {
        return dictService.update(DictDTO);
    }

    /**
     * 删除数据字典
     *
     * @param ids 数据字典ID
     * @return 影响行数
     */
    @Operation(summary = "删除数据字典", description = "删除数据字典")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody @NotNull List<Long> ids) {
        return dictService.deleteByIds(ids);
    }
}

