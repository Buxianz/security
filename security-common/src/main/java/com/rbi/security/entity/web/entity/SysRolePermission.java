package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysRolePermission)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
@Data
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

}