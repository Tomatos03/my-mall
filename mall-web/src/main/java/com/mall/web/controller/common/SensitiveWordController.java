package com.mall.web.controller.common;

import com.mall.api.service.ISensitiveWordService;
import com.mall.dto.common.SensitiveWordDTO;
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
 * @date : 2025/9/11
 */
@Tag(name = "敏感词接口", description = "敏感词相关操作")
@RestController
@RequestMapping("/v1/commonSensitiveWord")
public class SensitiveWordController {
    @Autowired
    ISensitiveWordService sensitiveWordService;

    /**
     * 校验敏感词
     *
     * @param sensitiveWordDTO 条件
     * @return 敏感词信息
     */
    @NoLogin
    @Operation(summary = "校验敏感词", description = "校验敏感词")
    @PostMapping("/checkSensitiveWord")
    public void checkSensitiveWord(@RequestBody SensitiveWordDTO sensitiveWordDTO) {
        sensitiveWordService.checkSensitiveWord(sensitiveWordDTO);
    }
}
