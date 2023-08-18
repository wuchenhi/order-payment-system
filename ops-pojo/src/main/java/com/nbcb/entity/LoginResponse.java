package com.nbcb.entity;

import lombok.Data;


@Data
public class LoginResponse {
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
    private LoginResult result;
}