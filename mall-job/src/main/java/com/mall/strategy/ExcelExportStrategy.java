package com.mall.strategy;

import com.mall.context.SpringContextHolder;
import com.mall.entity.CommonTaskDO;
import com.mall.entity.NotifyDO;
import com.mall.entity.condition.RequestCondition;
import com.mall.enums.ExcelBizType;
import com.mall.enums.TaskStatus;
import com.mall.enums.TaskType;
import com.mall.mapper.CommonNotifyMapper;
import com.mall.mapper.CommonTaskMapper;
import com.mall.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Slf4j
@Component
public class ExcelExportStrategy extends ScheduledTaskStrategy {
    private final CommonTaskMapper commonTaskMapper;
    private final CommonNotifyMapper commonNotifyMapper;

    @Autowired
    public ExcelExportStrategy(CommonNotifyMapper commonNotifyMapper, CommonTaskMapper commonTaskMapper) {
        this.commonNotifyMapper = commonNotifyMapper;
        this.commonTaskMapper = commonTaskMapper;
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
                updateStatus(TaskStatus.RUNNING, commonTaskDO, commonTaskMapper);

            exportExcel(excelBizType);
            pushSuccessNotice();
            updateStatus(TaskStatus.SUCCESS, commonTaskDO, commonTaskMapper);
        } catch (Exception e) {
            log.error("Excel导出失败:{}", e.getMessage());
            if (isExceedMaxFailureCount(commonTaskDO)) {
                updateStatus(TaskStatus.FAIL, commonTaskDO, commonTaskMapper);
                return;
            }
            increaseFailCount(commonTaskDO, commonTaskMapper);
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

    private void pushSuccessNotice() {
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

        commonNotifyMapper.insert(notifyDO);
    }
}
