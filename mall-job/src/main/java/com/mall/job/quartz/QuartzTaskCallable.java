package com.mall.job.quartz;

import com.mall.common.context.SpringContextHolder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/5
 */
public class QuartzTaskCallable implements Callable {
    private static final String JOB_METHOD_NAME = "doRun";
    private final Object target;
    private final Method method;
    private final String params;

    public QuartzTaskCallable(String beanName, String params)
            throws NoSuchMethodException, SecurityException {
        this.target = SpringContextHolder.getBean(beanName);
        this.params = params;
        this.method = target.getClass().getDeclaredMethod(JOB_METHOD_NAME, String.class);
    }

    @Override
    public Object call() throws Exception {
        ReflectionUtils.makeAccessible(method);
        return method.invoke(target, params);
//        return StrUtil.isNotBlank(params) ?
//                method.invoke(target, params) :
//                method.invoke(target);
    }
}
