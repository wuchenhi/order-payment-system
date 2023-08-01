package com.nbcb.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoodsResponse {
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
    private List<GoodsResult> result;
}