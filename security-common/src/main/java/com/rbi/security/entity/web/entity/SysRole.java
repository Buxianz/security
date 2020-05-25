package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    private Integer level;
    /**
     * 是否启用
     */
    private Integer enabled;
    /**
     * 操作人id
     */
    private Integer operatingStaff;
    /**
     *不在表中，添加角色信息时使用
     */
    private  List<SysRolePermission> sysRolePermissionList;

}