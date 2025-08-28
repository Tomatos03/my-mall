package com.mall.strategy;

import com.mall.entity.CommonTaskDO;
import com.mall.enums.TaskType;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
public abstract class ScheduledTaskStrategy {
    public static ScheduledTaskStrategy createScheduledTask(TaskType taskType) {
        // Java17+写法
        return switch (taskType) {
            case EXCEL_EXPORT -> new ExcelExportStrategy();
            default -> throw new IllegalStateException("Illegal TaskType");
        };
    }

    abstract public void execute(CommonTaskDO commonTaskDO);
}
