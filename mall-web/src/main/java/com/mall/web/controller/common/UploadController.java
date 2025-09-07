package com.mall.web.controller.common;

import com.mall.business.service.sys.UploadService;
import com.mall.common.enums.FileTypeEnum;
import com.mall.dto.sys.FileDTO;
import com.mall.security.annotation.NoLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
@Tag(name = "上传文件", description = "上传文件")
@RestController
@RequestMapping("/v1")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @NoLogin
    @Operation(summary = "上传文件接口")
    @PostMapping(value = "/file/upload")
    public FileDTO fileUpload(MultipartFile file) throws Exception {
        return uploadService.upload(file, FileTypeEnum.FILE);
    }

    @NoLogin
    @Operation(summary = "上传图片接口")
    @PostMapping(value = "/image/upload")
    public FileDTO imageUpload(MultipartFile file) throws Exception {
        return uploadService.upload(file, FileTypeEnum.IMAGE);
    }
}
