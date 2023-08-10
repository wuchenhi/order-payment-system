package com.nbcb.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopMsgConsumerKey  implements Serializable {
    private String groupName;

    private String msgTag;

    private String msgKey;
}