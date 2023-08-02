package com.nbcb.entity;

import lombok.Data;

@Data
public class PayResult {
    private String message;
    private Long payId;
    private Boolean status;
    private Long orderId;
}
