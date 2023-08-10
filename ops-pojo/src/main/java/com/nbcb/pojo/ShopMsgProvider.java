package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShopMsgProvider extends ShopMsgProviderKey  implements Serializable {
    private String id;

    private String msgTopic;

    private String msgBody;

    private Integer msgStatus;

    private Date createTime;
}