package com.mall.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
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
}
