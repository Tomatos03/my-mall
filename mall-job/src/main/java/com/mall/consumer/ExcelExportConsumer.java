package com.mall.consumer;

import com.mall.entity.NotifyDO;
import com.mall.mapper.CommonNotifyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mall.config.MQConfig.EXCEL_EXPORT_QUEUE;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 *
 */
@Component
@Slf4j
public class ExcelExportConsumer {
    @Autowired
    private CommonNotifyMapper commonNotifyMapper;

    @RabbitListener(queues = EXCEL_EXPORT_QUEUE)
    public void handler(Message message) {
        String content = new String(message.getBody());
        log.info("ExcelExportConsumer接收到消息：{}", content);
        pushSuccessNotice();
    }

    private void pushSuccessNotice() {
        NotifyDO notifyDO = NotifyDO.builder()
                                    .isPush(0)
                                    .type(1)
                                    .readStatus(0)
                                    .title("导出成功")
                                    .content("Excel导出成功")
                                    .isDel(0)
                                    .createUserId(0L)
                                    .createUserName("admin")
                                    .toUserId(9L)
                                    .build();

        commonNotifyMapper.insert(notifyDO);
    }
}
