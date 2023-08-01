package com.nbcb.entity;


@lombok.Data
public class OrderResponse {
    /*
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
    private OrderResultString result;
}