package com.mall.service;

import com.mall.entity.LogDO;
import com.mall.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
@Service
public class LogService {
    @Autowired
    LogMapper logMapper;

    public void insert(LogDO logDO) {
        logMapper.insert(logDO);
    }
}
