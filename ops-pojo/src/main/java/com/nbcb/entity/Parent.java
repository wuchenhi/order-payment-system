package com.nbcb.entity;

import lombok.Data;

@Data
public class Parent {
    /**
     * 父级分类id
     */
    private String id;
    /**
     * 父级所属层数
     */
    private long layer;
    /**
     * 父级分类名字
     */
    private String name;
    /**
     * 父级的父级，如果有的话
     */
    private Object parent;
}
