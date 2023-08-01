package com.nbcb.entity;

import lombok.Data;

/**
 * 商品品牌
 */
@Data
public class Brand {
    /**
     * 品牌描述
     */
    private Object desc;
    /**
     * 品牌id
     */
    private String id;
    /**
     * 品牌logo图片地址
     */
    private String logo;
    /**
     * 品牌名字
     */
    private String name;
    /**
     * 品牌英文名字
     */
    private String nameEn;
    /**
     * 品牌图片地址
     */
    private String picture;
    /**
     * 品牌产地
     */
    private Object place;
    /**
     * 品牌类型
     */
    private Object type;
}