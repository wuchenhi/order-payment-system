package com.nbcb.config;


import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.api.IUserService;
import com.nbcb.constant.ShopCode;
import com.nbcb.pojo.ShopUser;
import com.nbcb.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static com.alibaba.com.caucho.hessian.io.HessianInputFactory.log;

/**
 *  SpringBoot实现拦截器
 *  HandlerInterceptorAdapter:拦截器基类
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Reference
    private IUserService iUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            ShopUser shopUser = getUser(request, response);
            UserContext.setUser(shopUser);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();
            if (needLogin) {
                if (shopUser == null) {
                    render(response, ShopCode.SHOP_USER_IS_NULL);
                }
                key += ":" + shopUser.getUserId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(key);
            log.info(String.valueOf(count));
            if (count == null) {
                valueOperations.set(key, 1, second, TimeUnit.SECONDS);
                log.info(key);
            } else if (count < maxCount) {
                valueOperations.increment(key,1);
            } else {
                render(response, ShopCode.SHOP_SEKILL_TOOMOREREQUEST);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, ShopCode shopCode) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
//        ShopCode shopCode = RespBean.error(shopCode);
        printWriter.write(new ObjectMapper().writeValueAsString(shopCode));
        printWriter.flush();
        printWriter.close();
    }

    private ShopUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String userTicket = CookieUtil.getCookieValue(request, "userTicket");

        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }

        ShopUser user = (ShopUser) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;

       // return iUserService.getUserByCookie(userTicket, request, response);  // request 没有序列化 Dubbo调用问题

    }
}
