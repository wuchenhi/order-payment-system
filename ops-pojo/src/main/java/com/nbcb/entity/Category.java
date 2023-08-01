package com.nbcb.entity;

import lombok.Data;

@Data
public class Category {
    /**
     * 分类id
     */
    private String id;
    /**
     * 分类所属层数
     */
    private long layer;
    /**
     * 分类名字
     */
    private String name;
    /**
     * 分类-父级分类对象
     */
    private Parent parent;
}
