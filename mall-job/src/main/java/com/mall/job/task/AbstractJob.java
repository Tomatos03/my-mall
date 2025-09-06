package com.mall.job.task;

import com.mall.common.enums.JobResultEnum;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/6
 */
public abstract class AbstractJob {
    public abstract JobResultEnum doRun(String params);
}