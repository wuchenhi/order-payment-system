package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopUser;
import com.nbcb.pojo.ShopUserMoneyLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {
    /**
     * 根据ID查询用户
     *
     * @param userId
     * @return
     */
    ShopUser findOne(Long userId);

    /**
     * 根据name查询用户
     *
     * @param userName
     * @return
     */
    ShopUser findOneByName(String userName);

    /**
     * 更新用户余额
     *
     * @param userMoneyLog
     * @return
     */
    Result updateMoneyPaid(ShopUserMoneyLog userMoneyLog);

    /**
     * 根据cookie获取用户
     **/
    ShopUser getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
