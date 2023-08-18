package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nbcb.api.IGoodsService;
import com.nbcb.api.IUserService;
import com.nbcb.entity.*;
import com.nbcb.pojo.ShopGoods;
import com.nbcb.pojo.ShopUser;
import com.nbcb.pojo.UserLogin;
import com.nbcb.utils.IDWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.com.caucho.hessian.io.HessianInputFactory.log;

// 处理登录请求的接口
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private IDWorker idWorker;

    @GetMapping()
    public GoodsResponse getGoods() {
        // 查找所有货物
        List<ShopGoods> goodsList = goodsService.findAll();

        List<GoodsResult> results = new ArrayList<GoodsResult>();

        for(ShopGoods goods : goodsList) {
            GoodsResult goodsResult = new GoodsResult();
            goodsResult.setId(goods.getGoodsId().toString());
            goodsResult.setAlt(goods.getGoodsName());
            results.add(goodsResult);
        }

        GoodsResponse response = new GoodsResponse();

        response.setCode("1");
        response.setMsg("操作成功");
        response.setResult(results);

        return response;
    }

//    @RequestMapping("/toList")
//    public String toLogin(HttpSession session, Model model,
//                          @CookieValue("userTicket") String ticket) {
//        if (StringUtils.isEmpty(ticket)) {
//            return "login";
//        }
//
//        User user = (User) session.getAttribute(ticket);   //根据session辨识用户
//        if (null == user) {
//            return "login";
//        }
//        model.addAttribute("user", user);
//        return "goodsList";


    @GetMapping("/detail")
    public GoodsDetailResponse getOneGoods(Long id) {
        log.info(id.toString());
        // 查找货物
        ShopGoods goods = goodsService.findOne(id);

        GoodsDetailResult goodsDetailResult = new GoodsDetailResult();
        goodsDetailResult.setId(goods.getGoodsId().toString());
        goodsDetailResult.setName(goods.getGoodsName());
        goodsDetailResult.setPrice(goods.getGoodsPrice().toString());
        goodsDetailResult.setInventory(goods.getGoodsNumber());

        GoodsDetailResponse response = new GoodsDetailResponse();

        response.setCode("1");
        response.setMsg("操作成功");
        response.setResult(goodsDetailResult);
        log.info(goods.getGoodsId().toString());
        return response;
    }
}