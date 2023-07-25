package com.nbcb.test;

import com.nbcb.PayWebApplication;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopPay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayWebApplication.class)
public class PayWebTest {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${shop.pay.baseURI}")
    private String baseURI;

    @Value("${shop.pay.createPayment}")
    private String createPayment;

    @Value("${shop.pay.callbackPayment}")
    private String callbackPayment;
    @Test
    public void createPayment() throws IOException {
        Long orderId = 881137857090162689L;

        ShopPay pay = new ShopPay();
        pay.setOrderId(orderId);
        pay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_NO_PAY.getCode());
        pay.setPayAmount(new BigDecimal(850));
        Result result = restTemplate.postForObject(baseURI + createPayment, pay, Result.class);
        System.out.println(result);

    }

    @Test
    public void callbackPayment() throws IOException {
        Long orderId = 881137857090162689L;
        Long payId = 881155300768489472L;

        ShopPay pay = new ShopPay();
        pay.setPayId(payId);
        pay.setOrderId(orderId);
        pay.setIsPaid(ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode());
        Result result = restTemplate.postForObject(baseURI + callbackPayment, pay, Result.class);
        System.out.println(result);
    }
}
