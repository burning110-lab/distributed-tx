package com.qu.lele.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 20-09
 */
@Configuration
public class RabbitTemplateConfig {
    @Autowired
    private MsgConfirmCallBack msgConfirmCallBack;
    @Autowired
    private MsgReturnCallBack msgReturnCallBack;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(msgConfirmCallBack);
        rabbitTemplate.setReturnsCallback(msgReturnCallBack);
        return rabbitTemplate;
    }
}
