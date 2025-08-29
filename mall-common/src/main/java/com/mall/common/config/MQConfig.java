package com.mall.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mall.constant.MQConst.*;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
@Slf4j
@Configuration
public class MQConfig {
    @Bean
    public Queue excelExportQueue() {
        return QueueBuilder.durable(EXCEL_EXPORT_QUEUE)
                           .withArgument("x-message-ttl", DELAY_TIME) // 设置消息过期时间
                           .build();
    }

    @Bean
    public Exchange excelExportExchange() {
        return ExchangeBuilder.topicExchange(EXCEL_EXPORT_EXCHANGE)
                              .durable(true)
                              .build();
    }

    /**
     * 绑定excel导出交换机和队列
     */
    @Bean
    public Binding excelExportBinding(@Qualifier("excelExportQueue") Queue queue,
                                      @Qualifier("excelExportExchange") Exchange exchange) {
        return BindingBuilder.bind(queue)
                             .to(exchange)
                             .with(EXCEL_EXPORT_QUEUE_ROUTING_KEY)
                             .noargs();
    }

    // ObjectMapper: 描述如何序列化和反序列化对象
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
