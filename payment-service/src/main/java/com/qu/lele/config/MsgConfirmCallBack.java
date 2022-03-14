package com.qu.lele.config;

import com.qu.lele.dao.TblOrderEventDao;
import com.qu.lele.dto.EventStatus;
import com.qu.lele.dto.TblOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 20-11
 */
@Component
@Slf4j
public class MsgConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Resource
    private TblOrderEventDao tblOrderEventDao;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
         if (ack) {
             TblOrderEvent tblOrderEvent = new TblOrderEvent();
             tblOrderEvent.setId(correlationData.getId());
             tblOrderEvent.setProcess(EventStatus.PublishEvent.getStatus());
             tblOrderEvent.setUpdateTime(LocalDateTime.now());
             tblOrderEventDao.updateByPrimaryKeySelective(tblOrderEvent);
         } else {
             log.error("消息发布失败,ID:{}",correlationData.getId());
         }
    }
}
