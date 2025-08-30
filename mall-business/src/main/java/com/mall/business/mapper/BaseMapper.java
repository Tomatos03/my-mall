package com.mall.business.mapper;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/8/4
 */
public interface BaseMapper<K, V> {
    /**
     * 根据条件查询数据的数量
     *
     * @param v 实体类
     * @return 数量
     */
    int searchCount(V v);

    /**
     * 根据条件查询数据
     *
     * @param v 实体类
     * @return List<K> 实体类的集合
     * @throws DataAccessException 数据访问异常
     */
    List<K> searchByCondition(V v) throws DataAccessException;
}
