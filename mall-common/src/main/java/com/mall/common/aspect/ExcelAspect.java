package com.mall.common.aspect;

import com.mall.api.service.ITaskService;
import com.mall.common.annotation.ExcelExport;
import com.mall.entity.CommonTaskDO;
import com.mall.common.enums.ExcelBizTypeEnum;
import com.mall.common.enums.TaskStatusEnum;
import com.mall.common.enums.TaskTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Aspect
@Component
public class ExcelAspect {
    private final ITaskService taskService;

    @Autowired
    public ExcelAspect(ITaskService taskService) {
        this.taskService = taskService;
    }

    @Before("@annotation(com.mall.common.annotation.ExcelExport)")
    public void beforeAdvice(JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget()
                                  .getClass();
        // 获取切入方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取切入方法参数
        Object[] arguments = joinPoint.getArgs();
        // 获取目标类的所有方法
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            // 方法名相同、包含目标注解、方法参数个数相同（避免有重载）
            if (method.getName().equals(methodName) && method.getParameterTypes().length == arguments.length) {
                ExcelBizTypeEnum excelBizTypeEnum = method.getAnnotation(ExcelExport.class).value();

                CommonTaskDO excelExportTask = createExcelExportTask(excelBizTypeEnum);
                taskService.insert(excelExportTask);
            }
        }
    }

    private CommonTaskDO createExcelExportTask(ExcelBizTypeEnum excelBizType) {
        CommonTaskDO task = new CommonTaskDO();
        task.setName(excelBizType.getDesc());
        task.setStatus(TaskStatusEnum.WAITING.getValue());
        task.setFailureCount(0);
        task.setType(TaskTypeEnum.EXCEL_EXPORT.getValue());
        task.setBizType(excelBizType.getValue());
        task.setCreateUserId(0L);
        task.setCreateUserName("admin");
        return task;
    }
}


