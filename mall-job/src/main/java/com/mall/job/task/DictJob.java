package com.mall.job.task;

import com.mall.api.service.IDictService;
import com.mall.common.enums.JobResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@Slf4j
@Component
public class DictJob extends AbstractJob {
    @Autowired
    IDictService dictService;

    @Override
    public JobResultEnum doRun(String params) {
        dictService.refreshDictCache();
        return JobResultEnum.SUCCESS;
    }
}
