package com.rbi.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 此类是shiro认证缓存用户信息类
 */
@Data
public class AuthenticationUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String userName;//用户名
    private  String password;//用户密码
    private  Integer companyPersonnelId;
    private String salf;
    private Integer enabled;
}
