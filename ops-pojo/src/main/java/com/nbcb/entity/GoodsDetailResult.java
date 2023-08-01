package com.nbcb.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoodsDetailResult {
    /**
     * 商品品牌
     */
    private Brand brand;
    /**
     * 商品所属分类，多级以数组形式体现，如[一级分类，二级分类，三级分类]
     */
    private List<Category> categories;
    /**
     * 商品收藏总数
     */
    private long collectCount;
    /**
     * 商品评论总数
     */
    private long commentCount;
    /**
     * 商品描述
     */
    private String desc;
    /**
     * 商品详情
     */
    private Details details;
    /**
     * 商品折扣
     */
    private long discount;
    /**
     * 商品评价信息
     */
    private EvaluationInfo evaluationInfo;
    /**
     * 商品24小时热销列表
     */
    private List<HotByDay> hotByDay;
    /**
     * 商品id
     */
    private String id;
    /**
     * 商品库存数
     */
    private long inventory;
    /**
     * 商品是否收藏，用户未登录无值
     */
    private Object isCollect;
    /**
     * 商品是否为预售商品
     */
    private boolean isPreSale;
    /**
     * 商品主图集合
     */
    private List<String> mainPictures;
    /**
     * 商品主图视频合集
     */
    private List<String> mainVideos;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品原价
     */
    private String oldPrice;
    /**
     * 商品价格
     */
    private String price;
    /**
     * 商品推荐集合，仅APP有此数据
     */
    private Object recommends;
    /**
     * 商品销量
     */
    private long salesCount;
    /**
     * 商品同类商品集合
     */
    private List<SimilarProduct> similarProducts;
    /**
     * 商品sku集合
     */
    private List<Skus> skus;
    /**
     * 商品可选规格集合备注：规格集合一定要和skus集合下的specs 顺序保持一致
     */
    private List<ResultSpec> specs;
    /**
     * 商品spu编码
     */
    private String spuCode;
    /**
     * 商品用户地址列表，用户未登录时该字段为空
     */
    private Object userAddresses;
    /**
     * 商品主图视频比例，视频比例,1为1:1/16:9，2为3:4
     */
    private long videoScale;
}