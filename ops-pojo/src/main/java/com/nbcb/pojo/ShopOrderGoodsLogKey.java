package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopOrderGoodsLogKey implements Serializable {
    private Long goodsId;

    private Long orderId;

}