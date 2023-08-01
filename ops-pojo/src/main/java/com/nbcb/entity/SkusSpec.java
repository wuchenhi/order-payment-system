package com.nbcb.entity;

import lombok.Data;

@Data
public class SkusSpec {
    /**
     * 规格名字
     */
    private String name;
    /**
     * 可选值名称
     */
    private String valueName;
}
