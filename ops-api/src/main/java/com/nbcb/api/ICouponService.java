package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopCoupon;

/**
 * 优惠券接口
 */
public interface ICouponService {
    /**
     * 根据ID查询优惠券
     *
     * @param couponId
     * @return
     */
    ShopCoupon findOne(Long couponId);

    /**
     * 更新优惠券状态
     *
     * @param coupon
     * @return
     */
    Result updateCouponStatus(ShopCoupon coupon);
}

