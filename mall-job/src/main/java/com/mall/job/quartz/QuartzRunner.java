package com.mall.job.quartz;

import cn.hutool.core.collection.CollectionUtil;
import com.mall.api.service.ICommonJobService;
import com.mall.dto.condition.CommonJobConditionDTO;
import com.mall.dto.condition.PageConditionDTO;
import com.mall.entity.CommonJobDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mall.constant.JobConst.NOT_PAUSED;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Slf4j
@Component
public class QuartzRunner implements ApplicationRunner {
    @Autowired
    private ICommonJobService commonJobService;
    @Autowired
    private QuartzManage quartzManage;

    @Override
    public void run(ApplicationArguments args) {
        CommonJobConditionDTO condition = new CommonJobConditionDTO();
        condition.setPageSize(PageConditionDTO.ALL_PAGE);
        condition.setPauseStatus(NOT_PAUSED);

        List<CommonJobDO> jobs = commonJobService.searchByCondition(condition);
        if (CollectionUtil.isEmpty(jobs))
            return;

        for (CommonJobDO job : jobs) {
            quartzManage.addJob(job);
        }
        log.info("--------------------定时任务注入完成，注入定时的数量是：{}---------------------", jobs.size());
    }
}
