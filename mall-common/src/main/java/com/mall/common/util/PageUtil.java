package com.mall.common.util;

import com.mall.dto.PageDTO;
import com.mall.entity.condition.RequestCondition;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public final class PageUtil {
    private PageUtil() {};

    public static <K> PageDTO<K> emptyPage() {
        PageDTO<K> pageDTO = new PageDTO<>();
        pageDTO.setTotalCount(0);
        pageDTO.setTotalPage(0);
        pageDTO.setPageSize(0);

        return pageDTO;
    }

    public static <K> PageDTO<K> buildPageDTO(RequestCondition condition, List<K> dataList) {
        int pageSize = condition.getPageSize();
        int total = dataList.size();

        PageDTO<K> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage((total + pageSize - 1) / pageSize); // total / pageSize 向上取整数
        pageDTO.setPageNo(condition.getPageNo());
        pageDTO.setData(dataList);
        pageDTO.setPageSize(pageSize);
        pageDTO.setTotalCount(total);

        return pageDTO;
    }
}
