package com.mall.strategy;

import com.mall.entity.CommonTaskDO;
import com.mall.enums.TaskStatus;
import com.mall.enums.TaskType;
import com.mall.mapper.CommonTaskMapper;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
public abstract class ScheduledTaskStrategy {
    public abstract TaskType getTaskType();
    public abstract void execute(CommonTaskDO commonTaskDO);

    protected void updateStatus(TaskStatus status, CommonTaskDO commonTaskDO, CommonTaskMapper commonTaskMapper) {
        commonTaskDO.setStatus(status.getValue());
        commonTaskMapper.update(commonTaskDO);
    }

    protected void increaseFailCount(CommonTaskDO commonTaskDO, CommonTaskMapper commonTaskMapper) {
        commonTaskDO.setFailureCount(commonTaskDO.getFailureCount() + 1);
        commonTaskMapper.update(commonTaskDO);
    }

    protected boolean isExceedMaxFailureCount(CommonTaskDO commonTaskDO) {
        return commonTaskDO.getFailureCount() > CommonTaskDO.MAX_FAILURE_COUNT;
    }

    protected boolean isWaitingStatus(CommonTaskDO commonTaskDO) {
        return TaskStatus.WAITING.getValue()
                                 .equals(commonTaskDO.getStatus());
    }
}
