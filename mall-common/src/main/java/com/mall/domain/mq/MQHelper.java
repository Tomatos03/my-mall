package com.mall.domain.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
@Slf4j
@Component
public class MQHelper {
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public void send(String routeKey, Message message) {
//        rabbitTemplate.send(routeKey, message);
//    }

    /**
     * 发送消息到消息交换机
     *
     * @param exchange   交换机名称
     * @param routingKey 路由key
     * @param message    消息
     */
    public void send(String exchange, String routingKey, Object message) throws Exception{
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
