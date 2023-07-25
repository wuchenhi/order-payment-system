package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopCoupon implements Serializable {
    private Long couponId;

    private BigDecimal couponPrice;

    private Long userId;

    private Long orderId;

    private Integer isUsed;

    private Date usedTime;
}