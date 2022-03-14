package com.qu.lele.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 20-08
 */
@Configuration
public class ConfigMQ {

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(ConstantMQ.QUEUE_ORDER).build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(ConstantMQ.EXCHANGE_ORDER).build();
    }

    @Bean
    public Binding bindBuilding(Queue queue,TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(ConstantMQ.ROUTING_ORDER);
    }
}
