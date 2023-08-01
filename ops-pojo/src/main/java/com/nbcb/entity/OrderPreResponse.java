package com.nbcb.entity;

import lombok.Data;

@Data
public class OrderPreResponse {
    /**
     * 业务状态码，1成功, 其他失败
     */
    private String code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应结果
     */
    private OrderPreResult result;
}

