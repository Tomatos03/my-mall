package com.mall.job;

import com.mall.entity.CommonTaskDO;
import com.mall.entity.condition.CommonTaskCondition;
import com.mall.enums.TaskStatus;
import com.mall.enums.TaskType;
import com.mall.mapper.CommonTaskMapper;
import com.mall.strategy.ScheduledTaskStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
public class CommonTaskJob {
    @Autowired
    CommonTaskMapper commonTaskMapper;

    @Scheduled(fixedRate = 10000)
    public void handle() {
        List<CommonTaskDO> waitHandleTask = getNeedHandleTask();

        if (isNoTasks(waitHandleTask))
            return;

        for (CommonTaskDO task : waitHandleTask) {
            TaskType taskType = TaskType.fromValue(task.getType());
            ScheduledTaskStrategy scheduledTask = ScheduledTaskStrategy.createScheduledTask(taskType);
            scheduledTask.execute(task);
        }
    }

    private boolean isNoTasks(List<CommonTaskDO> waitHandleTask) {
        if (CollectionUtils.isEmpty(waitHandleTask)) {
            log.info("没有任务需要执行");
            return true;
        }
        return false;
    }

    private List<CommonTaskDO> getNeedHandleTask() {
        CommonTaskCondition commonTaskCondition = new CommonTaskCondition();

        List<Integer> statusQueue = List.of(
                TaskStatus.WAITING.getValue(),
                TaskStatus.RUNNING.getValue()
        );
        commonTaskCondition.setStatusList(statusQueue);

        return commonTaskMapper.searchByCondition(commonTaskCondition);
    }
}
