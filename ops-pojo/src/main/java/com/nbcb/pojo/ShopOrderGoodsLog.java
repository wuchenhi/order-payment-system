package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShopOrderGoodsLog extends ShopOrderGoodsLogKey  implements Serializable {
    private Integer goodsNumber;

    private Date logTime;
}