package com.mall.job.strategy;

import com.mall.api.service.ITaskService;
import com.mall.common.context.SpringContextHolder;
import com.mall.common.domain.mq.MQHelper;
import com.mall.entity.CommonTaskDO;
import com.mall.entity.NotifyDO;
import com.mall.entity.condition.RequestCondition;
import com.mall.common.enums.ExcelBizType;
import com.mall.common.enums.TaskStatus;
import com.mall.common.enums.TaskType;
import com.mall.business.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.mall.constant.MQConst.EXCEL_EXPORT_EXCHANGE;
import static com.mall.constant.MQConst.EXCEL_EXPORT_QUEUE_ROUTING_KEY;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Slf4j
@Component
public class ExcelExportStrategy extends ScheduledTaskStrategy {
    private final ITaskService taskService;
    private final MQHelper mqHelper;

    @Autowired
    public ExcelExportStrategy(ITaskService taskService, MQHelper mqHelper) {
        this.taskService = taskService;
        this.mqHelper = mqHelper;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EXCEL_EXPORT;
    }

    @Override
    public void execute(CommonTaskDO commonTaskDO) {
        ExcelBizType excelBizType = ExcelBizType.fromValue(commonTaskDO.getBizType());

        try {
            if (isWaitingStatus(commonTaskDO))
                updateStatus(TaskStatus.RUNNING, commonTaskDO, taskService);

            exportExcel(excelBizType);
            updateStatus(TaskStatus.SUCCESS, commonTaskDO, taskService);
            pushSuccessNotice();
        } catch (Exception e) {
            log.error("导出失败: {}", e.getMessage());
            log.error("Cause: {}", e.getCause().getMessage());
            if (isExceedMaxFailureCount(commonTaskDO)) {
                updateStatus(TaskStatus.FAIL, commonTaskDO, taskService);
                return;
            }
            increaseFailCount(commonTaskDO, taskService);
        }
    }

    private void exportExcel(ExcelBizType excelBizType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        RequestCondition condition = (RequestCondition) excelBizType.getConditionClass()
                                                                    .getDeclaredConstructor()
                                                                    .newInstance();
        String excelFileName = excelBizType.getDesc();
        CommonService commonService = (CommonService) SpringContextHolder.getBean(excelBizType.getServiceClass());
        commonService.export(condition, excelBizType.getDoClass(), excelFileName);
    }

    private void pushSuccessNotice() throws Exception {
        NotifyDO notifyDO = NotifyDO.builder()
                                    .isPush(0)
                                    .type(1)
                                    .readStatus(0)
                                    .title("导出成功")
                                    .content("Excel导出成功")
                                    .isDel(0)
                                    .createUserId(0L)
                                    .createUserName("admin")
                                    .toUserId(9L)
                                    .build();

        mqHelper.send(EXCEL_EXPORT_EXCHANGE, EXCEL_EXPORT_QUEUE_ROUTING_KEY, notifyDO);
    }
}
