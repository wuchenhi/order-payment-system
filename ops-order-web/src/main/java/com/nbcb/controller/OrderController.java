package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.nbcb.api.IGoodsService;
import com.nbcb.api.IOrderService;
import com.nbcb.api.IPayService;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.*;
import com.nbcb.pojo.ShopGoods;
import com.nbcb.pojo.ShopOrder;
import com.nbcb.pojo.ShopPay;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.alibaba.com.caucho.hessian.io.HessianInputFactory.log;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IOrderService orderService;

    @Reference
    private IPayService payService;

    @Reference
    private IGoodsService goodService;


    @GetMapping("/confirm/pre/{id}")
    public OrderPreResponse confirmOrderPre(@PathVariable("id") Long id) {
        log.info(id.toString());
        OrderPreResponse orderPreResponse = new OrderPreResponse();
        OrderPreResult orderPreResult = new OrderPreResult();

        ShopGoods goods = goodService.findOne(id);
        orderPreResult.setGoods(goods);

//        orderPreResult.setUserAddress();

        orderPreResponse.setCode("1");
        orderPreResponse.setMsg("操作成功");
        orderPreResponse.setResult(orderPreResult);
        return orderPreResponse;
    }

    @PostMapping("/confirm")
    public OrderResponse confirmOrder(@RequestBody ShopOrder order) {
        ShopGoods goods = goodService.findOne(order.getGoodsId());
        order.setGoodsPrice(goods.getGoodsPrice());
        order.setAddress("深圳");
        order.setGoodsNumber(1);
        order.setGoodsAmount(order.getGoodsPrice().multiply(new BigDecimal(order.getGoodsNumber())));
        order.setShippingFee(BigDecimal.ZERO);
        order.setOrderAmount(order.getShippingFee().add(order.getGoodsAmount()));
        order.setMoneyPaid(new BigDecimal(0));

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

        OrderResult orderResult = JSON.parseObject(result.getMessage(),OrderResult.class);

        OrderResultString orderResultString = new OrderResultString();

        orderResultString.setOrderId(orderResult.getOrderId().toString());
        orderResultString.setStatus(orderResult.getStatus());
        orderResultString.setPayAmount(orderResult.getPayAmount());
        orderResultString.setSourceCode(orderResult.getSourceCode());
        orderResultString.setMessage(orderResult.getMessage());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCode("1");
        orderResponse.setMsg("操作成功");
        orderResponse.setResult(orderResultString);

        log.info(result.getMessage());
        return orderResponse;
    }

    @GetMapping("/{id}")
    public OrderQueryResponse getOrder(@PathVariable("id") Long id) {
        log.info(id.toString());

        ShopOrder shopOrder = orderService.findOne(id);

        OrderQueryResponse orderQueryResponse = new OrderQueryResponse();

        orderQueryResponse.setCode("1");
        orderQueryResponse.setMsg("操作成功");
        orderQueryResponse.setResult(shopOrder);

        return orderQueryResponse;
    }
}
