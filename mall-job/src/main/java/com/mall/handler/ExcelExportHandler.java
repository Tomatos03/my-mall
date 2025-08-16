package com.mall.handler;

import com.mall.context.SpringContextHolder;
import com.mall.entity.CommonTaskDO;
import com.mall.entity.condition.CommonTaskCondition;
import com.mall.entity.condition.RequestCondition;
import com.mall.enums.ExcelBizType;
import com.mall.enums.TaskStatus;
import com.mall.mapper.CommonTaskMapper;
import com.mall.service.CommonService;
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
 * @date : 2025/8/15
 */
@Slf4j
@Component
public class ExcelExportHandler {
    @Autowired
    CommonTaskMapper commonTaskMapper;

    @Scheduled(fixedRate = 60000)
    public void handle() {
        List<CommonTaskDO> waitHandleTask = getNeedHandleTask();

        if (CollectionUtils.isEmpty(waitHandleTask)) {
            log.info("没有任务需要执行");
            return;
        }

        for (CommonTaskDO task : waitHandleTask) {
            doExport(task);
        }
    }

    private void doExport(CommonTaskDO commonTaskDO) {
        Integer excelBizType = commonTaskDO.getBizType();

        for (ExcelBizType currentExcelBizType : ExcelBizType.values()) {
            if (!currentExcelBizType.getValue().equals(excelBizType))
                continue;

            try {
                updateTaskStatus(TaskStatus.RUNNING, commonTaskDO);

                String fileName = currentExcelBizType.getDesc();
                RequestCondition condition =
                        (RequestCondition) Class.forName(currentExcelBizType.getDtoName())
                                                                     .getDeclaredConstructor()
                                                                     .newInstance();
                Class clazz = Class.forName(currentExcelBizType.getDoName());

                CommonService commonService = (CommonService) SpringContextHolder.getBean(Class.forName(currentExcelBizType.getServiceName()));
                commonService.export(condition, clazz,fileName);
                updateTaskStatus(TaskStatus.SUCCESS, commonTaskDO);
            } catch (Exception e) {
                log.info("Excel导出失败:{}", e.getMessage());
                commonTaskDO.setFailureCount(commonTaskDO.getFailureCount() + 1);
                if (commonTaskDO.getFailureCount() >= CommonTaskDO.MAX_FAILURE_COUNT)
                    commonTaskDO.setStatus(TaskStatus.FAIL.getValue());

                commonTaskMapper.update(commonTaskDO);
            }
            return;
        }
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

    private void updateTaskStatus(TaskStatus taskStatus, CommonTaskDO commonTaskDO) {
        commonTaskDO.setStatus(taskStatus.getValue());
        commonTaskMapper.update(commonTaskDO);
    }
}
