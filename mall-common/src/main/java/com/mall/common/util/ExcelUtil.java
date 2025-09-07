package com.mall.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public abstract class ExcelUtil {
    private static final int SHEET_SIZE = 3;
    private static final String FILE_STORE_PATH = "/tmp";
    private static final String UTF8 = StandardCharsets.UTF_8.toString();

    private ExcelUtil() {};

    /**
     * 导出excel到浏览器下载
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
        String path = getFilePath(fileName);

        FileOutputStream fileOutputStream = new FileOutputStream(path);
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
        String nowTime = LocalDateTime.now()
                                      .format(fmt);
        // 将中文字符进行重新编码防止下载时名称发生乱码
        return URLEncoder.encode(java.lang.String.format("%s_%s", fileName, nowTime), UTF8);
    }

    public static <K> String phasedExport(String fileName, Class<K> kClass, List<K> dataList) throws IOException {
        String path = getFilePath(fileName);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                ExcelWriter excelWriter = EasyExcel.write(fileOutputStream, kClass)
                                                   .build();
            ) {

            int dataSize = dataList.size();
            int sheetCount = (dataSize + SHEET_SIZE - 1) / SHEET_SIZE;
            for (int i = 0; i < sheetCount; ++i) {
                int l = i * SHEET_SIZE;
                int r = Math.min((i + 1) * SHEET_SIZE, dataSize);
                List<K> subList = dataList.subList(l, r);

                WriteSheet writeSheet = EasyExcel.writerSheet(i, "Sheet" + (i + 1))
                                                 .build();
                excelWriter.write(subList, writeSheet);
            }
        }
        return path;
    }

    private static String getFilePath(String fileName) {
        fileName = String.format("%s数据_%s.xlsx",
                                 fileName,
                                 LocalDateTime.now()
                                              .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return String.format("%s/%s", FILE_STORE_PATH, fileName);
    }
}
