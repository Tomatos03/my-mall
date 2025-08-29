package com.mall.job.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.mall.api.service.INotifyService;
import com.mall.business.service.CommonService;
import com.mall.common.context.SpringContextHolder;
import com.mall.common.enums.ExcelBizType;
import com.mall.dto.NotifyDTO;
import com.mall.entity.CommonTaskDO;
import com.mall.entity.NotifyDO;
import com.mall.entity.condition.RequestCondition;
import com.mall.job.service.WebSocketServer;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.mall.constant.MQConst.EXCEL_EXPORT_QUEUE;


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
        NotifyDTO notifyDTO = BeanUtil.copyProperties(notifyDO, NotifyDTO.class);
        WebSocketServer.sendMessage(notifyDTO);

        notifyDO.setIsPush(1);
        notifyService.update(notifyDO);
    }
}
