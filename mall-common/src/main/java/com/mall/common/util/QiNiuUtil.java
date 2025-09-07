package com.mall.common.util;

import cn.hutool.json.JSONUtil;
import com.mall.common.config.QiNiuConfig;
import com.mall.common.context.SpringBeanHolder;
import com.mall.common.enums.FileTypeEnum;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.InputStream;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
public final class QiNiuUtil {
    private static QiNiuConfig qiNiuConfig() {
        return SpringBeanHolder.getBean(QiNiuConfig.class);
    }

    /**
     * 将图片上传到七牛云
     */
    public static String upload(InputStream file, FileTypeEnum fileType) throws Exception {
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        QiNiuConfig qiNiuConfig = qiNiuConfig();
        Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());

        String upToken = null;
        String path = null;
        String mime = null;
        if (FileTypeEnum.FILE == fileType) {
            upToken = auth.uploadToken(qiNiuConfig.getFileBucketName());
            path = qiNiuConfig.getPictureDomain();
            mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (FileTypeEnum.IMAGE == fileType) {
            upToken = auth.uploadToken(qiNiuConfig.getPictureBucketName());
            path = qiNiuConfig.getFileDomain();
            mime = "image/png";
        }

        Response response = uploadManager.put(file, null, upToken, null, mime);
        DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
        return path + putRet.key;
    }
}