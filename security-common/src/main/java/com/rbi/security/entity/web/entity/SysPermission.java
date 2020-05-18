package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysPermission)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:09:49
 */
@Data
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -64220275797145303L;
    /**
    * 权限id
    */
    private Integer id;
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
    private String idt;
    
    private String udt;



}