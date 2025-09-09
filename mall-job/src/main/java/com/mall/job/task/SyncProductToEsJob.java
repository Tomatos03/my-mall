package com.mall.job.task;

import com.mall.api.service.es.IProductEsSyncService;
import com.mall.common.enums.JobResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/9
 */
@Slf4j
@Component
public class SyncProductToEsJob extends AbstractJob {
    @Autowired
    private IProductEsSyncService productEsSyncService;

    @Override
    public JobResultEnum doRun(String params) {
        productEsSyncService.syncProductToEs();
        return JobResultEnum.SUCCESS;
    }
}
