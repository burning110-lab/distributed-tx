package com.qu.lele.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 20-12
 */
@Component
@Slf4j
public class MsgReturnCallBack implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
      log.error("消息发布失败-->replyCode:{},replyText:{},exchange:{},routingKey:{},message:{}",
              returned.getReplyCode(),returned.getReplyText(),returned.getExchange(),returned.getRoutingKey(),
              returned.getMessage());
    }
}
