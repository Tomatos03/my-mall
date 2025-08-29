package com.mall.strategy;

import com.mall.context.SpringContextHolder;
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
            case EXCEL_EXPORT -> SpringContextHolder.getBean(ExcelExportStrategy.class);
            case SEND_EMAIL -> SpringContextHolder.getBean(EmailSendStrategy.class);
            default -> throw new IllegalStateException("Illegal TaskType");
        };
    }

    abstract public void execute(CommonTaskDO commonTaskDO);
}
