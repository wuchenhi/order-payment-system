package com.nbcb.entity;

import lombok.Data;

/**
 * 商品评价信息
 */
@Data
public class EvaluationInfo {
    /**
     * 评价的内容
     */
    private String content;
    /**
     * 评价创建时间
     */
    private String createTime;
    /**
     * 商品id
     */
    private String id;
    /**
     * 评价的用户信息
     */
    private Object member;
    /**
     * 客服回复
     */
    private Object officialReply;
    /**
     * 评价的订单信息
     */
    private Object orderInfo;
    /**
     * 评价的图片，带图评价
     */
    private Object pictures;
    /**
     * 评价点赞数
     */
    private long praiseCount;
    /**
     * 评价的好评率
     */
    private long praisePercent;
    /**
     * 评价的商品评分，取值范围0-5
     */
    private long score;
    /**
     * 评价的标记
     */
    private Object tags;
}
