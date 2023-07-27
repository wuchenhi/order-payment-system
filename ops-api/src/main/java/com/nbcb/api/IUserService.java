package com.nbcb.api;

import com.nbcb.entity.Result;
import com.nbcb.pojo.ShopUser;
import com.nbcb.pojo.ShopUserMoneyLog;

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
}
