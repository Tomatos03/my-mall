package com.mall.util;

import com.alibaba.excel.EasyExcel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/14
 */
public class ExcelUtil {
    public static final String FILE_STORE_PATH = "/tmp";
    private static final String UTF8 = StandardCharsets.UTF_8.toString();

    private ExcelUtil() {};

    /**
     * 导出excel
     *
     * @param fileName 文件名称
     * @param clazz    数据类型
     * @param data     数据
     * @param response 响应
     * @throws IOException 异常
     */
    public static <T> void export(String fileName, Class<T> clazz, List<T> data,
                                  HttpServletResponse response) throws IOException {
        String downloadName = getDownLoadFileName(fileName);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(UTF8);
        response.setHeader("Content-disposition", "attachment;filename=" + downloadName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), clazz)
                 .sheet(fileName)
                 .doWrite(data);
    }

    /**
     * 导出excel到指定存储位置
     *
     * @param fileName  文件名称
     * @param data      数据
     * @param clazz     数据类型
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/16 16:54
     */
    public static <T> void export(String fileName,  Class<T> clazz, List<T> data) throws FileNotFoundException, UnsupportedEncodingException {
        fileName = String.format("%s数据_%s.xlsx",
                                 fileName,
                                 LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s",
                                                                               FILE_STORE_PATH,
                                                                               fileName));
        EasyExcel.write(fileOutputStream, clazz)
                 .sheet(fileName)
                 .doWrite(data);
    }

    /**
     * 转换文件名称为filename_time格式
     *
     * @param fileName 文件名称
     * @return java.lang.String
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/14 18:07
     */
    private static String getDownLoadFileName(String fileName) throws UnsupportedEncodingException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowTime = LocalDateTime.now().format(fmt);
        // 将中文字符进行重新编码防止下载时名称发生乱码
        return URLEncoder.encode(String.format("%s_%s", fileName, nowTime), UTF8);
    }
}
