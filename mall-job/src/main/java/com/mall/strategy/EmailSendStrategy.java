package com.mall.strategy;

import cn.hutool.json.JSONUtil;
import com.mall.entity.CommonTaskDO;
import com.mall.enums.TaskStatus;
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
    @Autowired
    private EmailService emailService;
    @Autowired
    private CommonTaskMapper commonTaskMapper;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Override
    public void execute(CommonTaskDO commonTaskDO) {
        try {
            if (commonTaskDO.getStatus().equals(TaskStatus.WAITING.getValue()))
                updateStatus(TaskStatus.RUNNING, commonTaskDO);

            RemoteUserPOJO remoteUserPOJO = JSONUtil.toBean(commonTaskDO.getRequestParam(),
                                                                    RemoteUserPOJO.class);

            String htmlContent = generateHtmlContentStr(remoteUserPOJO);
            // TODO: 接收者邮箱暂时写死
            emailService.sendHtmlEmail(remoteUserPOJO.getEmail(), "异地登录提醒", htmlContent);

            updateStatus(TaskStatus.SUCCESS, commonTaskDO);
        } catch (Exception e) {
            log.error("数据导出异常，原因：", e);
            //失败次数加1
            commonTaskDO.setFailureCount(commonTaskDO.getFailureCount() + 1);
            //如果失败次数超过3次，则将状态改成失败，后面不再执行
            if (commonTaskDO.getFailureCount() >= CommonTaskDO.MAX_FAILURE_COUNT)
                updateStatus(TaskStatus.FAIL, commonTaskDO);
        }
    }

    private String generateHtmlContentStr(RemoteUserPOJO remoteUserPOJO) throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("remote_login_email.html");

        Map<String, Object> model = new HashMap<>(1);
        model.put("remoteUserPOJO", remoteUserPOJO);

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

    private void updateStatus(TaskStatus status, CommonTaskDO commonTaskDO) {
        commonTaskDO.setStatus(status.getValue());
        commonTaskMapper.update(commonTaskDO);
    }
}
