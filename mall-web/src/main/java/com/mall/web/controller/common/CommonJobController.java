package com.mall.web.controller.common;

import com.mall.api.service.ICommonJobService;
import com.mall.entity.CommonJobDO;
import com.mall.security.annotation.NoLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Tag(name = "动态定时任务", description = "动态定时任务相关操作")
@RestController
@RequestMapping("/v1/commonJob")
public class CommonJobController {
    @Autowired
    ICommonJobService commonJobService;

    /**
     * 添加定时任务
     *
     * @param commonJobDO 定时任务实体
     * @return 影响行数
     */
    @NoLogin
    @Operation(summary = "添加定时任务", description = "添加定时任务")
    @PostMapping("/insert")
    public int insert(@RequestBody CommonJobDO commonJobDO) {
        return commonJobService.insert(commonJobDO);
    }
}
