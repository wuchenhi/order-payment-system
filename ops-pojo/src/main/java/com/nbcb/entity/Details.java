package com.nbcb.entity;

import lombok.Data;

import java.util.List;

/**
 * 商品详情
 */
@Data
public class Details {
    /**
     * 商品详情图片集合地址
     */
    private List<String> pictures;
    /**
     * 商品属性集合
     */
    private List<Property> properties;
}
