package com.nbcb.entity;

import lombok.Data;

@Data
public class HotByDay {
    /**
     * 商品描述
     */
    private String desc;
    /**
     * 商品折扣
     */
    private Object discount;
    /**
     * 商品id
     */
    private String id;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品总订单数(销量)
     */
    private long orderNum;
    /**
     * 商品图片地址
     */
    private String picture;
    /**
     * 商品价格
     */
    private String price;
}
