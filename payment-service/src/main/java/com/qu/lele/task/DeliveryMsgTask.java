package com.qu.lele.task;

import com.alibaba.fastjson.JSONObject;
import com.qu.lele.config.ConstantMQ;
import com.qu.lele.config.MsgConfirmCallBack;
import com.qu.lele.config.MsgReturnCallBack;
import com.qu.lele.dao.TblOrderEventDao;
import com.qu.lele.dto.EventStatus;
import com.qu.lele.dto.TblOrderEvent;
import com.qu.lele.dto.TblOrderEventExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 17-09
 * 通过定时任务发布消息
 */
@Component
@Slf4j
public class DeliveryMsgTask {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MsgReturnCallBack msgReturnCallBack;
    @Autowired
    private MsgConfirmCallBack msgConfirmCallBack;
    @Resource
    private TblOrderEventDao tblOrderEventDao;
    @Value("${spring.rabbitmq.template.retry.max-attempts}")
    private String maxAttempts;

    @Scheduled(cron = "0/10 * * * * ?")
    public void task() {
        TblOrderEventExample orderEventExample = new TblOrderEventExample();
        orderEventExample.createCriteria()
                .andProcessIn(Arrays.asList(EventStatus.NewEvent.getStatus()));
        List<TblOrderEvent> tblOrderEvents = tblOrderEventDao.selectByExample(orderEventExample);
        int maxRetryCount = Integer.parseInt(maxAttempts);
        tblOrderEvents.stream().forEach(item -> {
            if (item.getRetryCount() <= maxRetryCount) {
                rabbitTemplate.setConfirmCallback(msgConfirmCallBack);
                rabbitTemplate.setReturnsCallback(msgReturnCallBack);
                rabbitTemplate.convertAndSend(ConstantMQ.EXCHANGE_ORDER,
                        ConstantMQ.ROUTING_ORDER, JSONObject.toJSONString(item),
                        message -> {
                          message.getMessageProperties().setMessageId(item.getId());
                          return message;
                        },new CorrelationData(item.getId()));
            } else {
                log.error("消息id:{},重试次数超过限制，请人工处理",item.getId());
            }
        });
    }
}
