package com.mall.dto.condition.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/** 实体
 *
 * @author Tomatos
 * @date 2025-09-05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonJobConditionDTO extends CommonConditionDTO {
 
     /**
      * ID
      */
     private Long id;

     /**
      * 定时任务名称
      */
     private String jobName;

     /**
      * 暂停状态 0：未暂停 1：已暂停
      */
     private Integer pauseStatus;

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

     /**
      * 创建人ID
      */
     private Long createUserId;

     /**
      * 创建人名称
      */
     private String createUserName;

     /**
      * 创建日期
      */
     private LocalDateTime createTime;

     /**
      * 修改人ID
      */
     private Long updateUserId;

     /**
      * 修改人名称
      */
     private String updateUserName;

     /**
      * 修改时间
      */
     private LocalDateTime updateTime;

     /**
      * 是否删除 1：已删除 0：未删除
      */
     private Integer isDel;
}
