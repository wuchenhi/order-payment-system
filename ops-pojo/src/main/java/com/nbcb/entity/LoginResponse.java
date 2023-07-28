package com.nbcb.entity;

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

    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public LoginResult getResult() { return result; }
    public void setResult(LoginResult value) { this.result = value; }
}