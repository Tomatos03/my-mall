package com.mall.strategy;

import cn.hutool.json.JSONUtil;
import com.mall.entity.CommonTaskDO;
import com.mall.enums.TaskStatus;
import com.mall.enums.TaskType;
import com.mall.mapper.CommonTaskMapper;
import com.mall.pojo.RemoteUserPOJO;
import com.mall.service.EmailService;
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

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Slf4j
@Component
public class EmailSendStrategy extends ScheduledTaskStrategy {
    private final EmailService emailService;
    private final CommonTaskMapper commonTaskMapper;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public EmailSendStrategy(EmailService emailService,
                             CommonTaskMapper commonTaskMapper,
                             FreeMarkerConfigurer freeMarkerConfigurer) {
        this.emailService = emailService;
        this.commonTaskMapper = commonTaskMapper;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SEND_EMAIL;
    }

    @Override
    public void execute(CommonTaskDO commonTaskDO) {
        try {
            if (isWaitingStatus(commonTaskDO))
                updateStatus(TaskStatus.RUNNING, commonTaskDO, commonTaskMapper);

            RemoteUserPOJO remoteUserPOJO = JSONUtil.toBean(commonTaskDO.getRequestParam(),
                                                                    RemoteUserPOJO.class);

            String htmlContent = generateHtmlContentStr(remoteUserPOJO);
            emailService.sendHtmlEmail(remoteUserPOJO.getEmail(), "异地登录提醒", htmlContent);

            updateStatus(TaskStatus.SUCCESS, commonTaskDO, commonTaskMapper);
        } catch (Exception e) {
            log.error("数据导出异常，原因：", e);
            if (isExceedMaxFailureCount(commonTaskDO)) {
                updateStatus(TaskStatus.FAIL, commonTaskDO, commonTaskMapper);
                return;
            }
            increaseFailCount(commonTaskDO, commonTaskMapper);
        }
    }

    private String generateHtmlContentStr(RemoteUserPOJO remoteUserPOJO) throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("remote_login_email.html");

        Map<String, Object> model = new HashMap<>(1);
        model.put("remoteUserPOJO", remoteUserPOJO);

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
