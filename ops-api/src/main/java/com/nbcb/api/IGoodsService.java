package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopGoods;
import com.nbcb.pojo.ShopOrderGoodsLog;

import java.util.List;

public interface IGoodsService {
    /**
     * 根据ID 查询 Goods
     *
     * @param goodsId
     * @return
     */
    ShopGoods findOne(Long goodsId);


    /**
     * 查询all Goods
     *
     * @param
     * @return
     */
    List<ShopGoods> findAll();

    /**
     * 扣减库存
     *
     * @param orderGoodsLog
     * @return
     */
    Result reduceGoodsNum(ShopOrderGoodsLog orderGoodsLog);
}
