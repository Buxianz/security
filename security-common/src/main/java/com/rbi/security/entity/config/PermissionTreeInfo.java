package com.rbi.security.entity.config;

import lombok.Data;

import java.util.List;
/**
 * 创建人：吴松达
 */
@Data
public class PermissionTreeInfo {
    private int id;
    private String permissionName;
    private String operateCode;
    private int parentId;
    private String parentName;
    private int systemId;
    private String systemName;
    private int enabled;
    /**
     * 权限描述
     */
    private String description;
    private List<PermissionTreeInfo> sysPermissionList;

    private int numberOfLayers;

}
