package com.wechat.pojo;

/**
 * User: Matt
 * Date: 2017/1/15
 * Time: 10:19
 * Description: accessToken
 */
public class AccessToken {

    private String access_token;
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}