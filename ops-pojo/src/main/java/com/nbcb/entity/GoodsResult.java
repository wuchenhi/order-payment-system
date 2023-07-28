package com.nbcb.entity;

public class GoodsResult {
    /**
     * 推荐别名
     */
    private String alt;
    /**
     * 推荐id
     */
    private String id;
    /**
     * 推荐图片
     */
    private String picture;
    /**
     * 推荐标题
     */
    private String title;

    public String getAlt() { return alt; }
    public void setAlt(String value) { this.alt = value; }

    public String getid() { return id; }
    public void setid(String value) { this.id = value; }

    public String getPicture() { return picture; }
    public void setPicture(String value) { this.picture = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }
}
