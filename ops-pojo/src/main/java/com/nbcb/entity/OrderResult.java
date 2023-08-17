package com.nbcb.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResult {
    private String message;
    private Long orderId;
    private Boolean status;
    private BigDecimal payAmount;
    private String sourceCode;
}
