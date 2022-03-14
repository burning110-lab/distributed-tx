package com.qu.lele.consume;

import com.alibaba.fastjson.JSONObject;
import com.qu.lele.config.ConstantMQ;
import com.qu.lele.dao.TblOrderEventDao;
import com.qu.lele.dto.EventStatus;
import com.qu.lele.dto.TblOrderEvent;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 21-25
 */
@Component
@Slf4j
public class OrderConsume {
    @Resource
    private TblOrderEventDao orderEventDao;

    @RabbitListener(queues = ConstantMQ.QUEUE_ORDER)
    public void receive(Message message, Channel channel) {
         try {
             String json = new String(message.getBody(), "utf-8");
             log.info(json);
             TblOrderEvent orderEvent = JSONObject.parseObject(json,
                     TblOrderEvent.class);
             orderEvent.setProcess(EventStatus.ConsumerEvent.getStatus());
             orderEvent.setCreateTime(LocalDateTime.now());
             orderEventDao.insertSelective(orderEvent);
             channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
         } catch (Exception e) {
             try {
                 if (message.getMessageProperties().getRedelivered()) {
                     log.error("消息id:{},消费异常，请人工处理");
                     channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
                 } else {
                     log.info("消息id:{},重新入队消费");
                     channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
                 }
             } catch (Exception ex) {
                 log.error("消息确认异常");
             }
         }
    }

}
