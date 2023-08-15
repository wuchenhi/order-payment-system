package com.nbcb.config;


import com.nbcb.pojo.ShopUser;

public class UserContext {

    private static ThreadLocal<ShopUser> userThreadLocal = new ThreadLocal<>();

    public static void setUser(ShopUser shopUser) {
        userThreadLocal.set(shopUser);
    }

    public static ShopUser getUser() {
        return userThreadLocal.get();
    }
}
