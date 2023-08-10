package com.nbcb.entity;


import lombok.Data;

/**
 * 响应结果
 */
@Data
public class LoginResult {
    /**
     * 用户名
     */
    private String account;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户生日
     */
    private String birthday;
    /**
     * 用户所在城市编码
     */
    private String cityCode;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户职业
     */
    private String profession;
    /**
     * 用户所在省份编码
     */
    private String provinceCode;
    /**
     * 用户token
     */
    private String token;
}
