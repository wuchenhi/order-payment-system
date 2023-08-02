package com.nbcb.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayStringResult{
    private String payId;

    private String orderId;

    private BigDecimal payAmount;

    private Integer isPaid;
}