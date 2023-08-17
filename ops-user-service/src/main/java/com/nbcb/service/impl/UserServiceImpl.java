package com.nbcb.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.nbcb.api.IUserService;
import com.nbcb.constant.ShopCode;
import com.nbcb.entity.Result;
import com.nbcb.exception.CastException;
import com.nbcb.mapper.ShopUserMapper;
import com.nbcb.mapper.ShopUserMoneyLogMapper;
import com.nbcb.pojo.ShopUser;
import com.nbcb.pojo.ShopUserMoneyLog;
import com.nbcb.pojo.ShopUserMoneyLogExample;
import com.nbcb.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("ALL")
@Slf4j
@Component
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private ShopUserMapper userMapper;

    @Autowired
    private ShopUserMoneyLogMapper userMoneyLogMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ShopUser findOne(Long userId) {
        if (userId == null) {
            CastException.cast(ShopCode.SHOP_REQUEST_PARAMETER_VALID);
        }
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public ShopUser findOneByName(String userName) {
        return userMapper.selectByName(userName);
    }

    @Override
    public Result updateMoneyPaid(ShopUserMoneyLog userMoneyLog) {
        //1校验参数是否合法
        if (userMoneyLog == null ||
                userMoneyLog.getUserId() == null ||
                userMoneyLog.getOrderId() == null ||
                userMoneyLog.getUseMoney() == null ||
                userMoneyLog.getUseMoney().compareTo(BigDecimal.ZERO) <= 0) {
            return new Result(ShopCode.SHOP_FAIL.getSuccess(), ShopCode.SHOP_USER_MONEY_REDUCE_FAIL.getCode(), ShopCode.SHOP_FAIL.getMessage());
        }

        //2查询订单余额使用日志
        ShopUserMoneyLogExample userMoneyLogExample = new ShopUserMoneyLogExample();
        ShopUserMoneyLogExample.Criteria criteria = userMoneyLogExample.createCriteria();
        criteria.andOrderIdEqualTo(userMoneyLog.getOrderId());
        criteria.andUserIdEqualTo(userMoneyLog.getUserId());
        long r = userMoneyLogMapper.countByExample(userMoneyLogExample);

        ShopUser user = userMapper.selectByPrimaryKey(userMoneyLog.getUserId());

        //3扣减余额
        if (userMoneyLog.getMoneyLogType().intValue() == ShopCode.SHOP_USER_MONEY_PAID.getCode().intValue()) {
            if (r > 0) {//已付款
                return new Result(ShopCode.SHOP_FAIL.getSuccess(), ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode(), ShopCode.SHOP_FAIL.getMessage());
            }
            //扣减余额
            user.setUserMoney(user.getUserMoney().subtract(userMoneyLog.getUseMoney()));

            userMapper.updateByPrimaryKey(user);
            log.info("扣减余额");
        }

        //4回退余额
        if (userMoneyLog.getMoneyLogType().intValue() == ShopCode.SHOP_USER_MONEY_REFUND.getCode().intValue()) {
            if (r < 0) {//未付款
                return new Result(ShopCode.SHOP_FAIL.getSuccess(), ShopCode.SHOP_ORDER_PAY_STATUS_NO_PAY.getCode(), ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getMessage());
            }
            //防止多次退款
            ShopUserMoneyLogExample userMoneyLogExample1 = new ShopUserMoneyLogExample();
            ShopUserMoneyLogExample.Criteria criteria1 = userMoneyLogExample1.createCriteria();
            criteria1.andUserIdEqualTo(userMoneyLog.getUserId());
            criteria1.andOrderIdEqualTo(userMoneyLog.getOrderId());
            criteria1.andMoneyLogTypeEqualTo(ShopCode.SHOP_USER_MONEY_REFUND.getCode());
            long r2 = userMoneyLogMapper.countByExample(userMoneyLogExample1);
            if (r2 > 0) {//已退过款
                return new Result(ShopCode.SHOP_FAIL.getSuccess(), ShopCode.SHOP_USER_MONEY_REDUCE_ALREADY.getCode(), ShopCode.SHOP_USER_MONEY_REDUCE_ALREADY.getMessage());
            }
            // 退款加余额
            user.setUserMoney(user.getUserMoney().add(userMoneyLog.getUseMoney()));
            userMapper.updateByPrimaryKey(user);
            log.info("回退余额");
        }

        //5记录订单余额使用日志
        userMoneyLog.setCreateTime(new Date());
        Result result;
        try {
            userMoneyLogMapper.insert(userMoneyLog);
            result = new Result(ShopCode.SHOP_SUCCESS.getSuccess(), ShopCode.SHOP_SUCCESS.getMessage());
            log.info("插入支付日志");
        } catch (Exception e) {
            log.info(e.toString());
            result = new Result(ShopCode.SHOP_FAIL.getSuccess(), ShopCode.SHOP_FAIL.getMessage());
        }
        return result;
    }

    @Override
    public ShopUser getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        ShopUser user = (ShopUser) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
