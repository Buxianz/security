package com.rbi.security.entity.web.role;

import com.rbi.security.tool.AbTreeInfo;
import lombok.Data;

import java.util.List;
@Data
public class RolePermissioonInfo{
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
    private String permissionName;
    private Integer systemId;
    private String systemName;
    private List<RolePermissioonInfo> rolePermissionInfos;
    private Integer parentId;
    private Integer enabled;
}
