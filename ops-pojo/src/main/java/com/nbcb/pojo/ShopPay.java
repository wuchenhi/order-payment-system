package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopPay implements Serializable {
    private Long payId;

    private Long orderId;

    private BigDecimal payAmount;

    private Integer isPaid;
}