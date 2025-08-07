package com.mall.entity.condition;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@Data
public class PageConditionEntity {
    // Xml条件查询中当PageSize小于等于0的时候不进行分页
    public static final int NO_PAGINATION = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码，默认从一页开始
     */
    private Integer pageNo = 1;

    /**
     * 每页大小，默认一页查询10条数据
     */
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 排序字段
     */
    private List<String> sortField;


    /**
     * 获取分页开始位置
     *
     * @return 分页开始位置
     */
    public Integer getPageBegin() {
        if (Objects.isNull(this.pageNo) || this.pageNo <= 0) {
            this.pageNo = 1;
        }

        if (Objects.isNull(this.pageSize)) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        return (this.pageNo - 1) * this.pageSize;
    }

    /**
     * 获取用户自定义排序集合
     *
     * @return 排序集合实体
     */
    public String getSortString() {
        List<String> sort = this.sortField;
        if (CollectionUtil.isEmpty(sort))
            return null;

        StringBuilder sortBuilder = new StringBuilder();
        for (String field : sort) {
            String[] values = field.split(",");
            sortBuilder.append(String.format("%s %s", values[0], values[1])).append(",");
        }
        return sortBuilder.deleteCharAt(sortBuilder.length() - 1).toString();
    }
}
