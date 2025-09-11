package com.mall.common.domain;

import com.mall.common.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/11
 */
@Component
public class IdGenerateHelp {
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    public long nextId() {
        return snowflakeIdWorker.nextId();
    }
}