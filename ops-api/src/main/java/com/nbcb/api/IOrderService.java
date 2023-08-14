package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopGoods;
import com.nbcb.pojo.ShopGoodsSeckill;
import com.nbcb.pojo.ShopOrder;
import com.nbcb.pojo.ShopUser;

public interface IOrderService {
    /**
     * 下单接口
     *
     * @param order
     * @return
     */
    Result confirmOrder(ShopOrder order);

    /**
     * 取消订单
     *
     * @param order
     * @return
     */
    Result cancelOrder(ShopOrder order);

    ShopOrder findOne(Long id);

    /**
     * 秒杀
     **/
    ShopOrder secKill(long userId, long goodsId);
}
