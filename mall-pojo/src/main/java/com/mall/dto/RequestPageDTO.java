package com.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestPageDTO {

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
     * 获取分页开始位置
     *
     * @return 分页开始位置
     */
    public Integer getPageBegin() {
        if (Objects.isNull(this.pageNo)) {
            this.pageNo = 1;
        }

        if (Objects.isNull(this.pageSize)) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        return (this.pageNo - 1) * this.pageSize;
    }
}
