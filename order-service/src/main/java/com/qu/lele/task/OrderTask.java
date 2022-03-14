package com.qu.lele.task;

import com.qu.lele.dao.TblOrderEventDao;
import com.qu.lele.dto.EventStatus;
import com.qu.lele.dto.TblOrderEvent;
import com.qu.lele.dto.TblOrderEventExample;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 22-16
 */
@Component
public class OrderTask {
    @Resource
    private TblOrderEventDao orderEventDao;

    @Scheduled(cron = "0/5 * * * * ?")
    public void task() {
        TblOrderEventExample example = new TblOrderEventExample();
        example.createCriteria()
                .andProcessIn(Arrays.asList(EventStatus.ConsumerEvent.getStatus()));
        List<TblOrderEvent> tblOrderEvents = orderEventDao.selectByExample(example);
        tblOrderEvents.parallelStream().forEach(item -> {
            //1.更新订单表支付状态
            //2.更新事件日志表
            item.setUpdateTime(LocalDateTime.now());
            item.setProcess(EventStatus.ProcessEvent.getStatus());
            orderEventDao.updateByPrimaryKeySelective(item);
        });
    }
}
