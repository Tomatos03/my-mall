package com.mall.business.service;

import com.mall.api.service.ICommonService;
import com.mall.dto.PageDTO;
import com.mall.entity.condition.PageCondition;
import com.mall.entity.condition.RequestCondition;
import com.mall.business.mapper.BaseMapper;
import com.mall.common.util.ExcelUtil;
import com.mall.common.util.TimeUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * K 表示DO, V表示ConditionDTO
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Slf4j
public abstract class CommonService<K, V> implements ICommonService<K, V> {
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
     * 导出数据到Excel到浏览器下载
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
    public PageDTO<K> searchByPage(V v) {
        RequestCondition condition = (RequestCondition)v;
        TimeUtil.fillTimeInterval(condition);

        List<K> dataList = getMapper().searchByCondition(v);

        PageDTO<K> pageDTO = new PageDTO<>();

        return buildPageDTO(condition, dataList, pageDTO);
    }

    private PageDTO<K> buildPageDTO(RequestCondition condition, List<K> dataList,
                                    PageDTO<K> pageDTO) {
        int pageSize = condition.getPageSize();
        int total = dataList.size();

        pageDTO.setTotalPage((total + pageSize - 1) / pageSize); // total / pageSize 向上取整数
        pageDTO.setPageNo(condition.getPageNo());
        pageDTO.setData(dataList);
        pageDTO.setPageSize(pageSize);
        pageDTO.setTotalCount(total);

        return pageDTO;
    }

    /**
     * 分段导出数据到Excel到指定储存位置
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
