// GoodsDetailResponse.java

package com.nbcb.entity;

import com.nbcb.pojo.ShopGoods;

public class GoodsDetailResponse {
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
    private ShopGoods result;

    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public ShopGoods  getResult() { return result; }
    public void setResult(ShopGoods value) { this.result = value; }
}