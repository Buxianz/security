package com.rbi.security.entity.web.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysPermission)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -64220275797145303L;
    /**
    * 权限id
    */
    private Long id;
    /**
    * 权限名称
    */
    private String permissionName;
    /**
    * 操作标识码
    */
    private String operateCode;
    /**
    * 父级权限id
    */
    private Integer parentId;
    /**
    * 权限描述
    */
    private String description;
    /**
    * 所属系统id
    */
    private Integer systemId;
    /**
    * 是否启用
    */
    private Integer enabled;
    /**
    * 创建时间
    */
    private Date idt;
    
    private Date udt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getIdt() {
        return idt;
    }

    public void setIdt(Date idt) {
        this.idt = idt;
    }

    public Date getUdt() {
        return udt;
    }

    public void setUdt(Date udt) {
        this.udt = udt;
    }

}