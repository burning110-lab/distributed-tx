package com.qu.lele.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 17-16
 */
@Data
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderNo;

    private Integer paymentStatus;

    private String paymentTime;

    private String paymentAccount;

}
