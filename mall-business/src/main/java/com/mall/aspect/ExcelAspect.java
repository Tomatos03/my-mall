package com.mall.aspect;

import com.mall.annotation.ExcelExport;
import com.mall.entity.CommonTaskDO;
import com.mall.enums.ExcelBizType;
import com.mall.enums.TaskStatus;
import com.mall.enums.TaskType;
import com.mall.mapper.CommonTaskMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
    private final CommonTaskMapper commonTaskMapper;

    public ExcelAspect(CommonTaskMapper commonTaskMapper) {
        this.commonTaskMapper = commonTaskMapper;
    }

    @Before("@annotation(com.mall.annotation.ExcelExport)")
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
                ExcelBizType excelBizTypeEnum = method.getAnnotation(ExcelExport.class).value();

                CommonTaskDO excelExportTask = createExcelExportTask(excelBizTypeEnum);
                commonTaskMapper.insert(excelExportTask);
            }
        }
    }

    private CommonTaskDO createExcelExportTask(ExcelBizType excelBizType) {
        CommonTaskDO task = new CommonTaskDO();
        task.setName(excelBizType.getDesc());
        task.setStatus(TaskStatus.WAITING.getValue());
//        task.setFailureCount(0);
        task.setType(TaskType.MENU.getValue());
        task.setBizType(excelBizType.getValue());
        task.setCreateUserId(0L);
        task.setCreateUserName("admin");
        return task;
    }
}


