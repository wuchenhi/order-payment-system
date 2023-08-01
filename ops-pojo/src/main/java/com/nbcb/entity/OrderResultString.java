package com.nbcb.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResultString {
    private String message;
    private String orderId;
    private Boolean status;
    private BigDecimal payAmount;
    private String sourceCode;
}
