package com.rbi.security.entity.web.entity;

import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
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
    * 角色所属组织id
    */
    private Integer organizationId;

    /**
     * 是否可看下级数据 0：不可以  1：可以
     * */
    private Integer whetherSee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getWhetherSee() {
        return whetherSee;
    }

    public void setWhetherSee(Integer whetherSee) {
        this.whetherSee = whetherSee;
    }
}