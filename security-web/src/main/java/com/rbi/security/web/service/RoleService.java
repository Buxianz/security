package com.rbi.security.web.service;

import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.tool.PageData;

public interface RoleService {
    void insertRole(SysRole sysRole) throws RuntimeException;
    void updateRole(SysRole sysRole) throws  RuntimeException;
    void deleteRole(int id) throws RuntimeException;
    PageData<PagingRole> pagingRole(int pageNo,int pageSize ,int startIndex) throws RuntimeException;
}
