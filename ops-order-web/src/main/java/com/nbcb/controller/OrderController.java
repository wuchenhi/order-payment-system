package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nbcb.api.IOrderService;
import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IOrderService orderService;

    @PostMapping("/confirm")
    public Result confirmOrder(@RequestBody ShopOrder order) {
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setGoodsId(order.getGoodsId());
        shopOrder.setUserId(order.getUserId());
        shopOrder.setCouponId(order.getCouponId());
        shopOrder.setAddress(order.getAddress());
        shopOrder.setGoodsNumber(order.getGoodsNumber());
        shopOrder.setGoodsPrice(order.getGoodsPrice());
        shopOrder.setGoodsAmount(order.getGoodsAmount());
        shopOrder.setShippingFee(order.getShippingFee());
        shopOrder.setOrderAmount(order.getOrderAmount());
        shopOrder.setMoneyPaid(order.getMoneyPaid());
        Result result = orderService.confirmOrder(shopOrder);
        return result;
    }
}
