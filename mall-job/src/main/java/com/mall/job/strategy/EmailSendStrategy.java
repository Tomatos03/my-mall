package com.mall.job.strategy;

import cn.hutool.json.JSONUtil;
import com.mall.api.service.sys.IEmailService;
import com.mall.api.service.sys.ITaskService;
import com.mall.entity.sys.CommonTaskDO;
import com.mall.common.enums.TaskTypeEnum;
import com.mall.pojo.RemoteUserPOJO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.mall.common.enums.TaskStatusEnum.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Slf4j
@Component
public class EmailSendStrategy extends ScheduledTaskStrategy {
    private final IEmailService emailService;
    private final ITaskService taskService;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public EmailSendStrategy(IEmailService emailService,
                             ITaskService taskService,
                             FreeMarkerConfigurer freeMarkerConfigurer) {
        this.emailService = emailService;
        this.taskService = taskService;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.SEND_EMAIL;
    }

    @Override
    public void execute(CommonTaskDO commonTaskDO) {
        try {
            if (isWaitingStatus(commonTaskDO))
                updateStatus(RUNNING, commonTaskDO, taskService);

            RemoteUserPOJO remoteUserPOJO = JSONUtil.toBean(commonTaskDO.getRequestParam(),
                                                                    RemoteUserPOJO.class);

            String htmlContent = generateHtmlContentStr(remoteUserPOJO);
            emailService.sendHtmlEmail(remoteUserPOJO.getEmail(), "异地登录提醒", htmlContent);

            updateStatus(SUCCESS, commonTaskDO, taskService);
        } catch (Exception e) {
            log.error("数据导出异常，原因：", e);
            if (isExceedMaxFailureCount(commonTaskDO)) {
                updateStatus(FAIL, commonTaskDO, taskService);
                return;
            }
            increaseFailCount(commonTaskDO, taskService);
        }
    }

    private String generateHtmlContentStr(RemoteUserPOJO remoteUserPOJO) throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("remote_login_email.html");

        Map<String, Object> model = new HashMap<>(1);
        model.put("remoteUserPOJO", remoteUserPOJO);

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
