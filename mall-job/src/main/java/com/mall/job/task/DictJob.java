package com.mall.job.task;

import com.mall.api.service.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/3
 */
@Slf4j
@Component
public class DictJob {
    @Autowired
    IDictService dictService;

    @Scheduled(fixedRate = 300000)
    public void run() {
        dictService.refreshDictCache();
    }
}
