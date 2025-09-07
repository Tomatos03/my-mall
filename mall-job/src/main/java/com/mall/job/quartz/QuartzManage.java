package com.mall.job.quartz;

import com.mall.common.exception.BusinessException;
import com.mall.entity.sys.CommonJobDO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.mall.constant.QuartzConst.JOB_KEY;
import static com.mall.constant.QuartzConst.JOB_NAME;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Slf4j
@Component
public class QuartzManage {
    @Autowired
    private Scheduler scheduler;

    public void addJob(CommonJobDO jobDO) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(QuartzExecutionJob.class).
                                            withIdentity(JOB_NAME + jobDO.getId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = TriggerBuilder.newTrigger()
                                                .withIdentity(JOB_NAME + jobDO.getId())
                                                .startNow()
                                                .withSchedule(CronScheduleBuilder.cronSchedule(jobDO.getCronExpression()))
                                                .build();

            cronTrigger.getJobDataMap().put(JOB_KEY, jobDO);

            //重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            //执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (jobDO.getPauseStatus()) {
                pauseJob(jobDO);
            }
        } catch (Exception e) {
            log.error("创建定时任务失败", e);
            throw new BusinessException("创建定时任务失败");
        }
    }

    /**
     * 更新job cron表达式
     *
     * @param jobDO /
     */
    public void updateJobCron(CommonJobDO jobDO) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + jobDO.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(jobDO);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDO.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(JOB_KEY, jobDO);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (jobDO.getPauseStatus()) {
                pauseJob(jobDO);
            }
        } catch (Exception e) {
            log.error("更新定时任务失败", e);
            throw new BusinessException("更新定时任务失败");
        }

    }

    /**
     * 删除一个job
     *
     * @param jobDO /
     */
    public void deleteJob(CommonJobDO jobDO) {
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + jobDO.getId());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            log.error("删除定时任务失败", e);
            throw new BusinessException("删除定时任务失败");
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobDO /
     */
    public void restoreJob(CommonJobDO jobDO) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + jobDO.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(jobDO);
            }
            JobKey jobKey = JobKey.jobKey(JOB_NAME + jobDO.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("恢复定时任务失败", e);
            throw new BusinessException("恢复定时任务失败");
        }
    }

    /**
     * 立即执行job
     *
     * @param jobDO /
     */
    public void runJobNow(CommonJobDO jobDO) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + jobDO.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(jobDO);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JOB_KEY, jobDO);
            JobKey jobKey = JobKey.jobKey(JOB_NAME + jobDO.getId());
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e) {
            log.error("定时任务执行失败", e);
            throw new BusinessException("定时任务执行失败");
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobDO /
     */
    public void pauseJob(CommonJobDO jobDO) {
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + jobDO.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务暂停失败", e);
            throw new BusinessException("定时任务暂停失败");
        }
    }
}
