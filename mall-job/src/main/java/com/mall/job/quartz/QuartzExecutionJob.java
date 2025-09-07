package com.mall.job.quartz;

import com.mall.api.service.ICommonJobLogService;
import com.mall.common.context.SpringBeanHolder;
import com.mall.common.enums.QuartzJobStatusEnum;
import com.mall.common.util.AuthenticatorUtil;
import com.mall.constant.JobUserConst;
import com.mall.dto.CommonJobLogDTO;
import com.mall.entity.CommonJobDO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.mall.constant.QuartzConst.JOB_KEY;

/**
 * Quartz 实际执行的异步任务
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
@Slf4j
public class QuartzExecutionJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        CommonJobDO job = (CommonJobDO) context.getMergedJobDataMap().get(JOB_KEY);
        CommonJobLogDTO commonJobLog = buildCommonJobLogDTO(job);
        try {
            AuthenticatorUtil.setMockAuthenticatedUser();
            recordJobLog(commonJobLog);

            try {
                executeQuartzJob(job);
                commonJobLog.setRunStatus(QuartzJobStatusEnum.SUCCESS.getValue());
            } catch (Exception e) {
                fillFailJobLog(e, commonJobLog);
            } finally {
                updateJobLog(commonJobLog);
            }
        } finally {
            AuthenticatorUtil.clearMockAuthenticatedUser();
        }
    }

    private void executeQuartzJob(CommonJobDO job) throws NoSuchMethodException, InterruptedException, ExecutionException {
        QuartzTaskCallable quartzTaskCallable = new QuartzTaskCallable(job.getBeanName(),
                                                                       job.getParams());
        ThreadPoolExecutor quartzThreadPool = SpringBeanHolder.getBean("quartzThreadPoolExecutor",
                                                                       ThreadPoolExecutor.class);
        Future future = quartzThreadPool.submit(quartzTaskCallable);
        future.get();
    }

    private void updateJobLog(CommonJobLogDTO commonJobLog) {
        commonJobLog.setEndTime(System.currentTimeMillis());
        SpringBeanHolder.getBean(ICommonJobLogService.class)
                        .update(commonJobLog);

        outputTaskDurationIfSuccess(commonJobLog);
    }

    private void outputTaskDurationIfSuccess(CommonJobLogDTO commonJobLog) {
        if (QuartzJobStatusEnum.SUCCESS == QuartzJobStatusEnum.of(commonJobLog.getRunStatus())) {
            long duration = commonJobLog.getEndTime() - commonJobLog.getStartTime();
            log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", commonJobLog.getJobName(), duration);
        }
    }

    private static CommonJobLogDTO buildCommonJobLogDTO(CommonJobDO job) {
        return CommonJobLogDTO.builder()
                              .jobId(job.getId())
                              .jobName(job.getJobName())
                              .beanName(job.getBeanName())
                              .methodName(job.getMethodName())
                              .params(job.getParams())
                              .cronExpression(job.getCronExpression())
                              .startTime(System.currentTimeMillis())
                              .runStatus(QuartzJobStatusEnum.RUNNING.getValue())
                              .createTime(LocalDateTime.now())
                              .createUserId(JobUserConst.DEFAULT_USER_ID)
                              .createUserName(JobUserConst.DEFAULT_USER_NAME)
                              .build();
    }

    private void fillFailJobLog(Exception e, CommonJobLogDTO commonJobLog) {
        log.error("任务执行失败，任务名称：{}", commonJobLog.getJobName(), e);
        commonJobLog.setRunStatus(QuartzJobStatusEnum.FAILURE.getValue());
        commonJobLog.setException(e.toString());
    }
    
    private void recordJobLog(CommonJobLogDTO commonJobLog) {
        log.info("任务准备执行，任务名称：{}", commonJobLog.getJobName());
        SpringBeanHolder.getBean(ICommonJobLogService.class)
                        .insert(commonJobLog);
    }
}
