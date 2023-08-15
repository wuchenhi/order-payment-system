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


    /**
     * 获取秒杀结果
     *
     * @param userId
     * @param goodsId
     * @return orderId 成功 ；-1 秒杀失败 ；0 排队中
     * @author LiChao
     * @operation add
     * @date 7:07 下午 2022/3/8
     **/
    long getResult(long userId, long goodsId);

    /**
     * 验证秒杀地址
     * @param userId
     * @param goodsId
     * @param path
     * @return
     */
    boolean checkPath(long userId, long goodsId, String path);
    /**
     * 生成秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(long userId, long goodsId);
}
