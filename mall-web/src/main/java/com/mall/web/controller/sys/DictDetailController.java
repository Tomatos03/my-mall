package com.mall.web.controller.sys;

import com.mall.api.service.IDictDetailService;
import com.mall.dto.DictDetailDTO;
import com.mall.dto.PageDTO;
import com.mall.dto.condition.DictDetailConditionDTO;
import com.mall.entity.DictDetailDO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@Tag(name = "部门操作", description = "部门接口")
@RestController
@RequestMapping("/v1/dictDetail")
public class DictDetailController {
    @Autowired
    private IDictDetailService dictDetailService;

    /**
     * 通过id查询数据字典详情信息
     *
     * @param id 系统ID
     * @return 数据字典详情信息
     */
    @Operation(summary = "通过id查询数据字典详情信息", description = "通过id查询数据字典详情信息")
    @GetMapping("/findById")
    public DictDetailDO findById(Long id) {
        return dictDetailService.findById(id);
    }

    /**
     * 根据条件查询数据字典详情列表
     *
     * @param condition 条件
     * @return 数据字典详情列表
     */
    @Operation(summary = "根据条件查询数据字典详情列表", description = "根据条件查询数据字典详情列表")
    @PostMapping("/searchByPage")
    public PageDTO<DictDetailDO> searchByPage(@RequestBody DictDetailConditionDTO condition) {
        return dictDetailService.searchByPage(condition);
    }


    /**
     * 根据条件查询数据字典详情列表
     *
     * @param condition 条件
     * @return 数据字典详情列表
     */
    @Operation(summary = "根据条件查询数据字典详情列表", description = "根据条件查询数据字典详情列表")
    @PostMapping("/searchDictDetail")
    public List<DictDetailDO> searchDictDetail(@RequestBody @NotNull DictDetailConditionDTO condition) {
        return dictDetailService.searchByCondition(condition);
    }

    /**
     * 添加数据字典详情
     *
     * @param dto 数据字典详情实体
     * @return 影响行数
     */
    @Operation(summary = "添加数据字典详情", description = "添加数据字典详情")
    @PostMapping("/insert")
    public int insert(@RequestBody DictDetailDTO dto) {
        return dictDetailService.insert(dto);
    }

    /**
     * 修改数据字典详情
     *
     * @param dto 数据字典详情实体
     * @return 影响行数
     */
    @Operation(summary = "修改数据字典详情", description = "修改数据字典详情")
    @PostMapping("/update")
    public int update(@RequestBody DictDetailDTO dto) {
        return dictDetailService.update(dto);
    }

    /**
     * 删除数据字典详情
     *
     * @param ids 数据字典详情ID
     * @return 影响行数
     */
    @Operation(summary = "删除数据字典详情", description = "删除数据字典详情")
    @PostMapping("/deleteByIds")
    public int deleteByIds(@RequestBody @NotNull List<Long> ids) {
        return dictDetailService.deleteByIds(ids);
    }
}
