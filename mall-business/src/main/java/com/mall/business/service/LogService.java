package com.mall.business.service;

import com.mall.api.service.ILogService;
import com.mall.entity.LogDO;
import com.mall.business.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
@Service
public class LogService implements ILogService {
    @Autowired
    private LogMapper logMapper;

    public void save(LogDO logDO) {
        logMapper.insert(logDO);
    }
}
