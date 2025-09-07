package com.mall.job.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.mall.api.service.INotifyService;
import com.mall.constant.JobUserConst;
import com.mall.dto.NotifyDTO;
import com.mall.entity.NotifyDO;
import com.mall.job.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.mall.constant.MQConst.EXCEL_EXPORT_QUEUE;
import static com.mall.constant.NotifyConst.PUSHED;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 *
 */
@Component
@Slf4j
public class NotifyConsumer {
    @Autowired
    private INotifyService notifyService;

    @RabbitListener(queues = EXCEL_EXPORT_QUEUE)
    public void handler(Message message) throws IOException {
        String notifyDOJson = new String(message.getBody());
        log.info("ExcelExportConsumer接收到消息：{}", notifyDOJson);

        NotifyDO notifyDO = JSONUtil.toBean(notifyDOJson, NotifyDO.class);
        pushSuccessNotice(notifyDO);
    }

    private void pushSuccessNotice(NotifyDO notifyDO) throws IOException {
        try {
            NotifyDTO notifyDTO = BeanUtil.copyProperties(notifyDO, NotifyDTO.class);
            WebSocketServer.sendMessage(notifyDTO);

            notifyDO.setIsPush(PUSHED);
            notifyService.update(notifyDO);
        } catch (Exception e) {
            log.error("推送成功消息失败, 原因:{}", e.getMessage());
        }
    }
}
