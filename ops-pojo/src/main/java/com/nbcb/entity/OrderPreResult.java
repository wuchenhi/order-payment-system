package com.nbcb.entity;

import com.nbcb.pojo.ShopGoods;
import lombok.Data;


/**
 * 响应结果
 */
@Data
public class OrderPreResult {
    /**
     * 订单商品集合
     */
    private ShopGoods goods;
    /**
     * 订单总计信息
     */
    private Summary summary;
    /**
     * 订单内用户地址列表
     */
    private UserAddress userAddress;
}
