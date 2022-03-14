package com.qu.lele.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

/**
 * @author: 屈光乐
 * @create: 2022-03-13 17-53
 * 为了消费者能够接收JSON数据，我们应该通过实现RabbitListenerConfigurer来定制RabbitMQ配置
 */
@Component
public class RabbitListenerConfig implements RabbitListenerConfigurer {
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(new MappingJackson2MessageConverter());
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }
}
