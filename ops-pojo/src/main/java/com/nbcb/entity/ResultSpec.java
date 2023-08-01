package com.nbcb.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResultSpec {
    /**
     * 规格id
     */
    private String id;
    /**
     * 规格名字
     */
    private String name;
    /**
     * 规格可选值集合
     */
    private List<Value> values;
}
