package com.nbcb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.nbcb.api.IUserService;
import com.nbcb.entity.LoginResponse;
import com.nbcb.entity.LoginResult;
import com.nbcb.pojo.ShopUser;
import com.nbcb.pojo.UserLogin;
import com.nbcb.utils.CookieUtil;
import com.nbcb.utils.IDWorker;
import com.nbcb.utils.MD5Util;
import com.nbcb.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 处理登录请求的接口
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IDWorker idWorker;

    @RequestMapping("/login")
    public LoginResponse doLogin(@RequestBody UserLogin userLogin, HttpServletRequest request, HttpServletResponse response) {
        // 根据用户名查找user对象
        ShopUser user = userService.findOneByName(userLogin.getAccount());

        // 密码判断(加密)
        String salt = "aabbcc"; // 应该从用户里拿到，但是我的数据库没这个信息
        if (!MD5Util.formPassToDBPass(userLogin.getPassword(), salt).equals(user.getUserPassword())) {
            ResponseEntity.badRequest().body("Invalid username or password");
        }

//        // 密码判断(明文)  弃用
//        if (user == null || ! user.getUserPassword().equals(userLogin.getPassword())) {
//            ResponseEntity.badRequest().body("Invalid username or password");
//        }

        //生成Cookie
        String userTicket = UUIDUtil.uuid();

        //将用户信息存入redis
        redisTemplate.opsForValue().set("user:" + userTicket, user);

        // request.getSession().setAttribute(userTicket, user);  //这样分布式环境会有问题 用redis存储解决

        CookieUtil.setCookie(request, response, "userTicket", userTicket);

        LoginResult result = new LoginResult();
        result.setToken(userTicket); // cookie作为token
        result.setAccount(user.getUserName());
        result.setId(String.valueOf(user.getUserId()));

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setCode("1");
        loginResponse.setMsg("操作成功");
        loginResponse.setResult(result);

        return loginResponse;
    }
        // return userService.doLongin(userLogin, request, response); // Request未序列化 Dubbo调用不行
}