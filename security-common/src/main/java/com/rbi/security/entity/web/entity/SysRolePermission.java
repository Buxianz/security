package com.rbi.security.entity.web.entity;

import java.io.Serializable;

/**
 * (SysRolePermission)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 204793512835792090L;
    /**
    * 权限角色关联id
    */
    private Integer id;
    /**
    * 权限id
    */
    private Integer permissionId;
    /**
    * 角色id
    */
    private Integer roleId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}