package com.mall.job.strategy;

import com.mall.api.service.sys.ITaskService;
import com.mall.entity.sys.CommonTaskDO;
import com.mall.common.enums.TaskStatusEnum;
import com.mall.common.enums.TaskTypeEnum;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
public abstract class ScheduledTaskStrategy {
    public abstract TaskTypeEnum getTaskType();
    public abstract void execute(CommonTaskDO commonTaskDO);

    protected void updateStatus(TaskStatusEnum status, CommonTaskDO commonTaskDO,
                                ITaskService taskService) {
        commonTaskDO.setStatus(status.getValue());
        taskService.update(commonTaskDO);
    }

    protected void increaseFailCount(CommonTaskDO commonTaskDO, ITaskService taskService) {
        commonTaskDO.setFailureCount(commonTaskDO.getFailureCount() + 1);
        taskService.update(commonTaskDO);
    }

    protected boolean isExceedMaxFailureCount(CommonTaskDO commonTaskDO) {
        return commonTaskDO.getFailureCount() > CommonTaskDO.MAX_FAILURE_COUNT;
    }

    protected boolean isWaitingStatus(CommonTaskDO commonTaskDO) {
        return TaskStatusEnum.WAITING.getValue()
                                     .equals(commonTaskDO.getStatus());
    }
}
