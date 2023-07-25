package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ShopGoods implements Serializable {

    private Long goodsId;

    private String goodsName;

    private Integer goodsNumber;

    private BigDecimal goodsPrice;

    private String goodsDesc;

    private Date addTime;
}