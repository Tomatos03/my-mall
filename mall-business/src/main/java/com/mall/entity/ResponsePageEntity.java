package com.mall.entity;

import com.mall.entity.condition.PageConditionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
@AllArgsConstructor
@Data
public class ResponsePageEntity<T> implements Serializable {

    private static final Integer ZERO = 0;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;


    /**
     * 总记录数
     */
    private Integer totalCount;

    /**
     * 数据
     */
    private List<T> data;


    /**
     * 构建分页响应实体
     *
     * @param pageCondition 分页请求实体
     * @param <T>               数据类型
     * @return ResponsePageEntity实体
     */
    public static <T> ResponsePageEntity<T> buildEmpty(PageConditionEntity pageCondition) {
        return build(pageCondition, 0, new ArrayList<>(0));
    }


    /**
     * 构建分页响应实体
     *
     * @param pageCondition 分页请求实体
     * @param totalCount        总记录数
     * @param data              数据
     * @param <T>               数据类型
     * @return ResponsePageEntity实体
     */
    public static <T> ResponsePageEntity<T> build(PageConditionEntity pageCondition, Integer totalCount,
                                                  List<T> data) {
        Integer totalPage = getTotalPage(pageCondition.getPageSize(), totalCount);
        return new ResponsePageEntity(pageCondition.getPageNo(), pageCondition.getPageSize(), totalPage, totalCount, data);
    }


    private static Integer getTotalPage(Integer pageSize, Integer totalCount) {
        if (Objects.isNull(pageSize) || Objects.isNull(totalCount)) {
            return ZERO;
        }

        if (pageSize <= 0 || totalCount <= 0) {
            return ZERO;
        }
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }
}
