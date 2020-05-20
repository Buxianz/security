package com.rbi.security.entity.config;

import lombok.Data;

import java.util.List;

/**
 * 创建人：吴松达
 */
@Data
public class SystemMenuPermisson {
    private int id;
    private String systemName;
    private List<PermissionTreeInfo> permissionTreeInfoList;

    public SystemMenuPermisson() {
    }

    public SystemMenuPermisson(int id, String systemName) {
        this.id = id;
        this.systemName = systemName;
    }
}
