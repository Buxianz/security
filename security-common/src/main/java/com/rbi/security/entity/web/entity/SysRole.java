package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = -90027394134821660L;
    /**
    * 角色id
    */
    private Integer id;
    /**
    * 角色名称
    */
    private String roleName;
    /**
     * 是否可看下级数据 0：不可以  1：可以
     * */
    private Integer whetherSee;

}