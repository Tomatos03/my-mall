package com.mall.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/22
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            /*
             * 前端Js number类型能够表示的最大整数值为2^53 - 1,
             * 后端如果使用Long或long, 前端接收数据可能发生溢出需
             * 要将Long或long类型序列化为 String对象,
             */
            if (converter instanceof MappingJackson2HttpMessageConverter messageConverter) {
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
                simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

                messageConverter.getObjectMapper().registerModule(simpleModule);
                return;
            }
        }
    }
}
