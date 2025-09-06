package com.mall.business.service;

import com.mall.common.enums.FileTypeEnum;
import com.mall.common.exception.BusinessException;
import com.mall.common.util.QiNiuUtil;
import com.mall.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
@Service
@Slf4j
public class UploadService {

    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileType 文件类型
     * @return 文件实体
     * @throws Exception
     */
    public FileDTO upload(MultipartFile file, FileTypeEnum fileType) throws Exception {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            log.error("传入的文件名不能为空");
            throw new BusinessException("传入的文件名不能为空");
        }
        if (!this.validateFileName(fileName)) {
            log.error("文件名应仅包含汉字、字母、数字、下划线和点号");
            throw new BusinessException("文件名应仅包含汉字、字母、数字、下划线和点号");
        }

        InputStream inputStream = file.getInputStream();
        String url = QiNiuUtil.upload(inputStream, fileType);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(fileName);
        fileDTO.setDownloadUrl(url);
        log.info("文件成功上传到OOS, url:{}", fileDTO.getDownloadUrl());
        return fileDTO;
    }

    /**
     * 验证文件名称：仅包含 汉字、字母、数字、下划线和点号
     *
     * @param fileName 文件名称
     * @return 返回true表示符合要求
     */
    private boolean validateFileName(String fileName) {
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5_\\._\\-]+$";
        return fileName.matches(regex);
    }
}
