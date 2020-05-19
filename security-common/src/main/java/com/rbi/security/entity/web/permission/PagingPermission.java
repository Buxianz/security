package com.rbi.security.entity.web.permission;

import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.entity.SysRole;
import lombok.Data;

import java.util.List;

/**
 * 权限信息分页查看类
 */

@Data
public class PagingPermission {
    SysPermission sysPermission=new SysPermission();
    SysRole sysRole=new SysRole();

}
