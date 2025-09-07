package com.mall.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonJobDO extends CommonDO {
     /**
      * 定时任务名称
      */
     private String jobName;

     /**
      * 暂停状态 0：未暂停 1：已暂停
      */
     private Boolean pauseStatus;

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
      * 备注
      */
     private String remark;
}
