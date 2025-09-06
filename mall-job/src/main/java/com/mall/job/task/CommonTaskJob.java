package com.mall.job.task;

import com.mall.api.service.ITaskService;
import com.mall.common.enums.JobResultEnum;
import com.mall.entity.CommonTaskDO;
import com.mall.dto.condition.CommonTaskConditionDTO;
import com.mall.common.enums.TaskStatusEnum;
import com.mall.job.strategy.ScheduledTaskStrategy;
import com.mall.job.strategy.ScheduledTaskStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Component
@Slf4j
public class CommonTaskJob extends AbstractJob{
    @Autowired
    private ITaskService taskService;
    @Autowired
    private ScheduledTaskStrategyFactory strategyFactory;

    @Override
    public JobResultEnum doRun(String params) {
        List<CommonTaskDO> waitHandleTask = getNeedHandleTask();

        if (isNoTasks(waitHandleTask))
            return JobResultEnum.SUCCESS;

        for (CommonTaskDO task : waitHandleTask) {
            ScheduledTaskStrategy scheduledTask = strategyFactory.getStrategy(task.getType());
            scheduledTask.execute(task);
        }
        return JobResultEnum.SUCCESS;
    }

    private boolean isNoTasks(List<CommonTaskDO> waitHandleTask) {
        if (CollectionUtils.isEmpty(waitHandleTask)) {
            log.info("没有任务需要执行");
            return true;
        }
        return false;
    }

    private List<CommonTaskDO> getNeedHandleTask() {
        CommonTaskConditionDTO commonTaskCondition = new CommonTaskConditionDTO();

        List<Integer> statusQueue = List.of(
                TaskStatusEnum.WAITING.getValue(),
                TaskStatusEnum.RUNNING.getValue()
        );
        commonTaskCondition.setStatusList(statusQueue);

        return taskService.searchByCondition(commonTaskCondition);
    }
}
