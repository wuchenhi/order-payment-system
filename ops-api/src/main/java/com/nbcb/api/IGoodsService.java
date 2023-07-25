package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopGoods;
import com.nbcb.pojo.ShopOrderGoodsLog;

public interface IGoodsService {
    /**
     * 根据ID 查询 Goods
     *
     * @param goodsId
     * @return
     */
    ShopGoods findOne(Long goodsId);

    /**
     * 扣减库存
     *
     * @param orderGoodsLog
     * @return
     */
    Result reduceGoodsNum(ShopOrderGoodsLog orderGoodsLog);
}
