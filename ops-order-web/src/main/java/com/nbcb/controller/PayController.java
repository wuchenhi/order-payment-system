package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.nbcb.api.IPayService;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.*;
import com.nbcb.pojo.ShopPay;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Reference
    private IPayService payService;

    /**
     * 创建支付单据(用户支付调用)
     *
     * @param
     * @return
     */
    @PostMapping("/createPay")
    PayResponse createPay(@RequestBody ShopPay shopPay) {
        PayResponse payResponse = new PayResponse();
        PayStringResult pay = new PayStringResult();

        shopPay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_NO_PAY.getCode());

        Result result = payService.createPayment(shopPay);

        PayResult payResult = JSON.parseObject(result.getMessage(),PayResult.class);
        shopPay.setPayId(payResult.getPayId());
        pay.setPayAmount(shopPay.getPayAmount());
        pay.setOrderId(shopPay.getOrderId().toString());
        pay.setPayId(shopPay.getPayId().toString());
        pay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_NO_PAY.getCode());
        payResponse.setResult(pay);
        payResponse.setCode("1");
        payResponse.setMsg("操作成功");
        return payResponse;
    }

    /**
     * 完成支付单据
     *
     * @param
     * @return
     */
    @PostMapping("/finishPay")
    PayResponse finishPay(@RequestBody ShopPay payIn) {

        ShopPay shopPay = payService.findOne(payIn.getPayId());
        shopPay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode());
        payService.callbackPayment(shopPay);

        PayStringResult pay = new PayStringResult();
        pay.setPayAmount(shopPay.getPayAmount());
        pay.setOrderId(shopPay.getOrderId().toString());
        pay.setPayId(shopPay.getPayId().toString());
        pay.setIsPaid(shopPay.getIsPaid());

        PayResponse payResponse = new PayResponse();
        payResponse.setResult(pay);
        payResponse.setCode("1");
        payResponse.setMsg("操作成功");
        return payResponse;
    }

    /**
     * 支付回调(第三方调用)
     *
     * @param shopPay
     * @return
     */
    @PostMapping("/callbackPayment")
    Result callbackPayment(@RequestBody ShopPay shopPay) {
        return payService.callbackPayment(shopPay);
    }


    @PostMapping("/callbackPay")
    Result callbackPay(@RequestBody ShopPay shopPay) {
        // 回调
        return payService.callbackPayment(shopPay);
    }
}