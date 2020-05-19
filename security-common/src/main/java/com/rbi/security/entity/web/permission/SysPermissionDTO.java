package com.rbi.security.entity.web.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysPermissionDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

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

    List<?> sysPermissionDTO;
}
