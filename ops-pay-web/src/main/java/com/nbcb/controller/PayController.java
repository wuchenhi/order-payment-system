package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nbcb.api.IPayService;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopPay;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
        Result result = payService.createPayment(pay);

        log.info(result.getMessage());

        Timer timer=new Timer();


        // 自动支付 回调
        pay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode());

        //休眠2秒，模拟正在办理业务
        try {
            Thread.sleep(2000);//毫秒数
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return payService.callbackPayment(pay);
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
