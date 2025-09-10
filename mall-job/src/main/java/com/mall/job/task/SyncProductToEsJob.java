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
// TODO 暂时开启事务, 当任务失败的时候存在一半成功的状态
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
