package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nbcb.api.IPayService;
import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopPay;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Reference
    private IPayService payService;

    /**
     * 创建支付单据(用户支付调用)
     *
     * @param shopPay
     * @return
     */
    @PostMapping("/createPayment")
    Result createPayment(@RequestBody ShopPay shopPay) {
        return payService.createPayment(shopPay);
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
}
