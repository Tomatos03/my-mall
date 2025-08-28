package com.mall.strategy;

import com.mall.context.SpringContextHolder;
import com.mall.entity.CommonTaskDO;
import com.mall.entity.NotifyDO;
import com.mall.entity.condition.RequestCondition;
import com.mall.enums.ExcelBizType;
import com.mall.enums.TaskStatus;
import com.mall.mapper.CommonNotifyMapper;
import com.mall.mapper.CommonTaskMapper;
import com.mall.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Slf4j
@Component
public class ExcelExportStrategy extends ScheduledTaskStrategy {
    @Autowired
    CommonTaskMapper commonTaskMapper;
    @Autowired
    CommonNotifyMapper commonNotifyMapper;

    @Override
    public void execute(CommonTaskDO commonTaskDO) {
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

    private void updateTaskStatus(TaskStatus taskStatus, CommonTaskDO commonTaskDO) {
        commonTaskDO.setStatus(taskStatus.getValue());
        commonTaskMapper.update(commonTaskDO);
    }
}
