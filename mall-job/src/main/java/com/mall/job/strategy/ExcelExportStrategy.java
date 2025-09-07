package com.mall.job.strategy;

import com.mall.api.service.sys.ITaskService;
import com.mall.business.service.sys.CommonService;
import com.mall.common.context.SpringBeanHolder;
import com.mall.common.domain.mq.MQHelper;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.common.enums.TaskStatusEnum;
import com.mall.common.enums.TaskTypeEnum;
import com.mall.entity.sys.CommonTaskDO;
import com.mall.entity.sys.NotifyDO;
import com.mall.dto.condition.sys.CommonConditionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.mall.constant.DelStatusConst.NOT_DELETED;
import static com.mall.constant.MQConst.*;
import static com.mall.constant.NotifyConst.NOT_PUSHED;
import static com.mall.constant.NotifyTypeConst.EXCEL_EXPORT;
import static com.mall.constant.ReadStatus.UNREAD;

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
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.EXCEL_EXPORT;
    }

    @Override
    public void execute(CommonTaskDO commonTaskDO) {
        ExcelBizTypeEnum excelBizType = ExcelBizTypeEnum.fromValue(commonTaskDO.getBizType());

        try {
            if (isWaitingStatus(commonTaskDO))
                updateStatus(TaskStatusEnum.RUNNING, commonTaskDO, taskService);

            exportExcel(excelBizType);
            updateStatus(TaskStatusEnum.SUCCESS, commonTaskDO, taskService);
            sendNoticeToMQ();
        } catch (Exception e) {
            log.error("导出失败: {}", e.getMessage());
            log.error("Cause: {}", e.getCause().getMessage());
            if (isExceedMaxFailureCount(commonTaskDO)) {
                updateStatus(TaskStatusEnum.FAIL, commonTaskDO, taskService);
                return;
            }
            increaseFailCount(commonTaskDO, taskService);
        }
    }

    private void exportExcel(ExcelBizTypeEnum excelBizType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        CommonConditionDTO condition = (CommonConditionDTO) excelBizType.getConditionClass()
                                                                        .getDeclaredConstructor()
                                                                        .newInstance();
        String excelFileName = excelBizType.getDesc();
        CommonService commonService = (CommonService) SpringBeanHolder.getBean(excelBizType.getServiceClass());
        commonService.export(condition, excelBizType.getDoClass(), excelFileName);
    }

    private void sendNoticeToMQ() throws Exception {
        NotifyDO notifyDO = NotifyDO.builder()
                                    .isPush(NOT_PUSHED)
                                    .type(EXCEL_EXPORT)
                                    .readStatus(UNREAD)
                                    .title("导出成功")
                                    .content("Excel导出成功")
                                    .isDel(NOT_DELETED)
                                    .createUserId(0L)
                                    .createUserName("admin")
                                    .toUserId(9L)
                                    .build();

        mqHelper.send(EXCEL_EXPORT_EXCHANGE, EXCEL_EXPORT_QUEUE_ROUTING_KEY, notifyDO);
    }
}
