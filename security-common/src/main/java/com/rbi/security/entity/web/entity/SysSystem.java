package com.rbi.security.entity.web.entity;

import java.io.Serializable;

/**
 * (SysSystem)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
public class SysSystem implements Serializable {
    private static final long serialVersionUID = 635112247982037272L;
    /**
    * 系统id
    */
    private Long id;
    /**
    * 系统key
    */
    private String systemKey;
    /**
    * 系统名称
    */
    private String systemName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}