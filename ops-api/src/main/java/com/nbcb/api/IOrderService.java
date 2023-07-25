package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopOrder;

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

}
