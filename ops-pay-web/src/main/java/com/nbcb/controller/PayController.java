package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nbcb.api.IPayService;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopPay;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.alibaba.com.caucho.hessian.io.HessianInputFactory.log;

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

    @GetMapping("/createPay")
    Result createPay(Long orderId, BigDecimal payAmount) {
        ShopPay pay = new ShopPay();
        pay.setOrderId(orderId);
        pay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_NO_PAY.getCode());
        pay.setPayAmount(payAmount);

        return payService.createPayment(pay);
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
        // 自动支付 回调
        shopPay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode());
        return payService.callbackPayment(shopPay);
    }


//    @GetMapping("/{id}")
//    public PayQueryResponse getOrder(@PathVariable("id") Long id) {
//        log.info(id.toString());
//
//        ShopPay shopOrder = payService.findOne(id);
//
//        PayQueryResponse orderQueryResponse = new PayQueryResponse();
//
//        orderQueryResponse.setCode("1");
//        orderQueryResponse.setMsg("操作成功");
//        orderQueryResponse.setResult(shopOrder);
//
//        return orderQueryResponse;
//    }
}
