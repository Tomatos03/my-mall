package com.mall.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class CommonJobLogDO extends CommonDO {
     /**
      * 定时任务ID
      */
     private Long jobId;

     /**
      * 定时任务名称
      */
     private String jobName;

     /**
      * 执行状态 1：执行中 2：暂停 3：成功 4：失败
      */
     private Integer runStatus;

     /**
      * bean名称
      */
     private String beanName;

     /**
      * 方法名称
      */
     private String methodName;

     /**
      * cron 表达式
      */
     private String cronExpression;

     /**
      * 参数
      */
     private String params;

     /**
      * 开始执行时间
      */
     private LocalDateTime startTime;

     /**
      * 执行结束时间
      */
     private LocalDateTime endTime;

     /**
      * 异常信息
      */
     private String exception;
}
