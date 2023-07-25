package com.nbcb.exception;

import com.nbcb.constant.ShopCode;

public class CustomerException extends RuntimeException {

    private ShopCode shopCode;

    public CustomerException(ShopCode shopCode) {
        this.shopCode = shopCode;
    }
}
