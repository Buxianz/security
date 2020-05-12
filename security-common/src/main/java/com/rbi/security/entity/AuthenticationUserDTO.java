package com.rbi.security.entity;

import java.io.Serializable;

public class AuthenticationUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String userName;//用户名
    private  String password;//用户密码
    private String salt;
    private Integer enabled;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
