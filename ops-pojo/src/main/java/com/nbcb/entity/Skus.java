package com.nbcb.entity;

import lombok.Data;

import java.util.List;

@Data
public class Skus {
    /**
     * sku的id
     */
    private String id;
    /**
     * 当前规格库存
     */
    private long inventory;
    /**
     * 当前规格原价
     */
    private String oldPrice;
    /**
     * 当前规格价格
     */
    private String price;
    /**
     * sku的编码
     */
    private String skuCode;
    /**
     * 规格集合(和详情中specs的顺序一定要保持一致)
     */
    private List<SkusSpec> specs;
}
