package com.mall.job.controller;

import com.mall.api.service.ISensitiveWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/10
 */
@Tag(name = "初始化接口", description = "初始化相关操作")
@RestController
@RequestMapping("/init")
public class InitController {

    @Autowired
    private ISensitiveWordService commonSensitiveWordService;

    /**
     * 初始化敏感词
     */
    @Operation(summary = "初始化敏感词", description = "初始化敏感词")
    @GetMapping("/initSensitiveWord")
    public Boolean initSensitiveWord(@RequestParam("type") Integer type,
                                     @RequestParam("filePath") String filePath) {
        return commonSensitiveWordService.initSensitiveWord(type, filePath);
    }
}


