package com.qu.lele.service;

import com.alibaba.fastjson.JSONObject;
import com.qu.lele.dao.TblOrderEventDao;
import com.qu.lele.dto.EventStatus;
import com.qu.lele.dto.Payment;
import com.qu.lele.dto.TblOrderEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 17-10
 */
@Service
public class PaymentService {
    @Resource
    private TblOrderEventDao tblOrderEventDao;


    public void insertEvent(Payment payment) {
        String jsonString = JSONObject.toJSONString(payment);
        TblOrderEvent tblOrderEvent = new TblOrderEvent();
        tblOrderEvent.setId(UUID.randomUUID().toString().replaceAll("-",""));
        tblOrderEvent.setOrderType("Payment");
        tblOrderEvent.setContent(jsonString);
        tblOrderEvent.setProcess(EventStatus.NewEvent.getStatus());
        tblOrderEvent.setCreateTime(LocalDateTime.now());
        tblOrderEventDao.insert(tblOrderEvent);
    }
}
