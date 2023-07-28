package com.nbcb.entity;

import java.util.List;

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

    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public List<GoodsResult> getResult() { return result; }
    public void setResult(List<GoodsResult> value) { this.result = value; }
}