package com.rbi.security.entity.web.role;

import lombok.Data;

import java.util.List;

/**
 * 角色信息分页查看类
 */
@Data
public class PagingRole {
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

    private Integer level;
    /**
     * 是否启用
     */
    private Integer enabled;
    /**
     * 角色拥有的权限
     */
    private List<RolePermissioonInfo> rolePermissionInfoList;

}
