package com.mall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/29
 */
@Slf4j
@Configuration
public class MQConfig {
    /**
     * excel导出交换机
     */
    public static final String EXCEL_EXPORT_EXCHANGE = "excel_export_exchange";
    /**
     * excel导出队列
     */
    public static final String EXCEL_EXPORT_QUEUE = "excel_export_queue";
    /**
     * excel导出路由key
     */
    public static final String EXCEL_EXPORT_QUEUE_ROUTING_KEY = "excel_export.#";
    /**
     * 延迟时间 （单位：ms(毫秒)）
     */
    public static final Integer DELAY_TIME = 10000;

    @Bean
    public Queue excelExportQueue() {
        return QueueBuilder.durable(EXCEL_EXPORT_QUEUE)
                           .withArgument("x-message-ttl", DELAY_TIME)
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

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
