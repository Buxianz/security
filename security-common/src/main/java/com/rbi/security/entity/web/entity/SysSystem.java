package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysSystem)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
@Data
public class SysSystem implements Serializable {
    private static final long serialVersionUID = 635112247982037272L;
    /**
    * 系统id
    */
    private Integer id;
    /**
    * 系统key
    */
    private String systemKey;
    /**
    * 系统名称
    */
    private String systemName;



}