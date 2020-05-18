package com.rbi.security.web.service;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.tool.PageData;

public interface UserService {
    void insertUser(SysUser sysUser) throws RuntimeException;
    void updateUserInfo(SysUser sysUser) throws RuntimeException;
    void deleteUser(int id)throws  RuntimeException;
    PageData<PagingUser> pagingQueryUserInfo() throws RuntimeException;

}
