package com.mall.business.service.sys;

import com.mall.api.service.sys.ILogService;
import com.mall.entity.sys.LogDO;
import com.mall.business.mapper.sys.LogMapper;
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
