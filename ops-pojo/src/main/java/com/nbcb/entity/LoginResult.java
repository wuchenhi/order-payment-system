package com.nbcb.entity;


/**
 * 响应结果
 */
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

    public String getAccount() { return account; }
    public void setAccount(String value) { this.account = value; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String value) { this.avatar = value; }

    public String getBirthday() { return birthday; }
    public void setBirthday(String value) { this.birthday = value; }

    public String getCityCode() { return cityCode; }
    public void setCityCode(String value) { this.cityCode = value; }

    public String getGender() { return gender; }
    public void setGender(String value) { this.gender = value; }

    public String getid() { return id; }
    public void setid(String value) { this.id = value; }

    public String getMobile() { return mobile; }
    public void setMobile(String value) { this.mobile = value; }

    public String getNickname() { return nickname; }
    public void setNickname(String value) { this.nickname = value; }

    public String getProfession() { return profession; }
    public void setProfession(String value) { this.profession = value; }

    public String getProvinceCode() { return provinceCode; }
    public void setProvinceCode(String value) { this.provinceCode = value; }

    public String getToken() { return token; }
    public void setToken(String value) { this.token = value; }
}
