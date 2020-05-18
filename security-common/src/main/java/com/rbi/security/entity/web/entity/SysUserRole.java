package com.rbi.security.entity.web.entity;

import java.io.Serializable;

/**
 * (SysUserRole)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 597830476070006180L;
    /**
    * 角色用户关联id
    */
    private Integer id;
    /**
    * 角色id
    */
    private Integer roleId;
    /**
    * 用户id
    */
    private Integer userId;
    /**
     * 操作人员（公司人员信息id）
     */
    private int operatingStaff;
    /**
     * 创建时间
     */
    private String idt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getOperatingStaff() {
        return operatingStaff;
    }

    public void setOperatingStaff(int operatingStaff) {
        this.operatingStaff = operatingStaff;
    }
    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }
}