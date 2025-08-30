package com.mall.api.service;

import com.mall.dto.PageDTO;

import java.io.IOException;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public interface ICommonService<K, V> {
    void export(V condition, Class<K> kClass, String fileName) throws IOException;


    PageDTO<K> searchByPage(V v);
}
