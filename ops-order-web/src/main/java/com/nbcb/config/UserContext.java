package com.nbcb.config;

import com.nbcb.pojo.ShopUser;

public class UserContext {

    // ThreadLocal就是保证在同一个线程中能获取到同一个数据对象
    private static ThreadLocal<ShopUser> userThreadLocal = new ThreadLocal<>();

    public static void setUser(ShopUser shopUser) {
        userThreadLocal.set(shopUser);
    }

    public static ShopUser getUser() {
        return userThreadLocal.get();
    }
}
