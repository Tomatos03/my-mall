package com.mall.service;

import com.mall.domain.ResponsePage;
import com.mall.entity.condition.PageCondition;
import com.mall.entity.condition.RequestCondition;
import com.mall.mapper.BaseMapper;
import com.mall.util.ExcelUtil;
import com.mall.util.TimeUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Slf4j
public abstract class CommonService<K, V> {

    /**
     * 获取定义的Mapper
     *
     * @return com.mall.mapper.BaseMapper<K,V>
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:05
     */
    protected abstract BaseMapper<K, V> getMapper();

    /**
     * 导出数据到Excel
     *
     * @param condition 条件实体
     * @param response  响应对象
     * @param kClazz    待添加对象Class对象
     * @param fileName  文件名称
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:28
     */
    public void export(V condition, HttpServletResponse response, Class<K> kClazz, String fileName) throws IOException {
        RequestCondition requestCondition = (RequestCondition) condition;
        requestCondition.setPageSize(PageCondition.ALL_PAGE);
        TimeUtil.fillTimeInterval(requestCondition);

        List<K> dataList = getMapper().searchByCondition(condition);
        ExcelUtil.export(fileName, kClazz, dataList, response);
    }

    /**
     * 分页查询
     *
     * @param v 条件实体
     * @return com.mall.domain.ResponsePage<K> 分页数据
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:44
     */
    public ResponsePage<K> searchByPage(V v) {
        RequestCondition requestCondition = (RequestCondition)v;
        TimeUtil.fillTimeInterval(requestCondition);

        List<K> dataList = getMapper().searchByCondition(v);
        return ResponsePage.build(requestCondition, dataList.size(), dataList);
    }

    /**
     * 导出数据到Excel
     *
     * @param condition 条件实体
     * @param kClass    待添加对象实体
     * @param fileName  文件名称
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:28
     */
    public void export(V condition, Class<K> kClass, String fileName) throws IOException {
        RequestCondition requestCondition = (RequestCondition) condition;
        requestCondition.setPageSize(PageCondition.ALL_PAGE);
        TimeUtil.fillTimeInterval(requestCondition);

        List<K> dataList = getMapper().searchByCondition(condition);
        ExcelUtil.phasedExport(fileName, kClass, dataList);
    }
}
