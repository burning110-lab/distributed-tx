package com.qu.lele.controller;

import com.qu.lele.dto.Payment;
import com.qu.lele.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 17-10
 * 模拟第三支付的回调接口
 */
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/thirdPay")
    public String thirdPayCallBack(@RequestBody Payment payment) {
        //1.第一步更新支付流水表
        //2.插入事件表
        paymentService.insertEvent(payment);
        return "success";
    }
}
