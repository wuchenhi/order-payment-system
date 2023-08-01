package com.nbcb.entity;

import lombok.Data;

@Data
public class Value {
    /**
     * 可选值备注
     */
    private String desc;
    /**
     * 可选值名称
     */
    private String name;
    /**
     * 可选值图片地址
     */
    private String picture;
}
