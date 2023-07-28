package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.nbcb.api.IOrderService;
import com.nbcb.api.IUserService;
import com.nbcb.entity.LoginResponse;
import com.nbcb.entity.LoginResult;
import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopOrder;
import com.nbcb.pojo.ShopUser;
import com.nbcb.pojo.UserLogin;
import com.nbcb.utils.IDWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alibaba.com.caucho.hessian.io.HessianInputFactory.log;

// 处理登录请求的接口
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private IUserService userService;

    @Autowired
    private IDWorker idWorker;

    @PostMapping("/login")
    public LoginResponse checkUser(@RequestBody UserLogin userLogin) {
        // 根据用户名查找user对象
        ShopUser user = userService.findOneByName(userLogin.getAccount());

        if (user == null || ! user.getUserPassword().equals(userLogin.getPassword())) {
            ResponseEntity.badRequest().body("Invalid username or password");
        }
        // 生成token字符串
        String token = String.valueOf(idWorker.nextId());

        LoginResult result = new LoginResult();
        result.setToken(token);
        result.setAccount(user.getUserName());
        result.setid(String.valueOf(user.getUserId()));

        LoginResponse response = new LoginResponse();

        response.setCode("1");
        response.setMsg("操作成功");
        response.setResult(result);

        log.info(user.getUserName());
        log.info(user.getUserPassword());
        return response;
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> checkUser(@RequestBody UserLogin userLogin) {
//        // 根据用户名查找user对象
//        ShopUser user = userService.findOneByName(userLogin.getAccount());
//
//        if (user == null || ! user.getUserPassword().equals(userLogin.getPassword())) {
//            ResponseEntity.badRequest().body("Invalid username or password");
//        }
//        // 生成token字符串
//        String token = String.valueOf(idWorker.nextId());
//
//        // 将token放入JSON对象中，并返回该对象
//        JSONObject json = new JSONObject();
//        json.put("token", token);
//        log.info(user.getUserName());
//        log.info(user.getUserPassword());
//        return ResponseEntity.ok(json.toJSONString());
//    }
}