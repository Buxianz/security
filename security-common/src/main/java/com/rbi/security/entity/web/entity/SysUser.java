package com.rbi.security.entity.web.entity;

import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 360253428883446542L;
    /**
    * 用户id
    */
    private Integer id;
    /**
    * 账号
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 公司人员id（外键）
    */
    private Integer companyPersonnelId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCompanyPersonnelId() {
        return companyPersonnelId;
    }

    public void setCompanyPersonnelId(Integer companyPersonnelId) {
        this.companyPersonnelId = companyPersonnelId;
    }

}