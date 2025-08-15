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
     * @param v        数据对象
     * @param response 响应对象
     * @param clazz    数据类型
     * @param fileName 文件名称
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:28
     */
    public void export(V v, HttpServletResponse response, Class<K> clazz, String fileName) throws IOException {
        RequestCondition requestCondition = (RequestCondition) v;
        requestCondition.setPageSize(PageCondition.ALL_PAGE);
        TimeUtil.fillTimeInterval(requestCondition);

        List<K> dataList = getMapper().searchByCondition(v);
        ExcelUtil.export(fileName, clazz, dataList, response);
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
}
