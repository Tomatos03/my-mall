package com.mall.dto.sys;

import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
@Getter
@Setter
public class FileDTO {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 下载地址
     */
    private String downloadUrl;
}
