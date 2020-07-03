package com.rbi.security.web.DAO;

import com.rbi.security.entity.AuthenticationUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface LoginVerificationDAO {
    /**
     * 获取用户的基本信息
     * @param
     * @return
     */
    @Select("SELECT id,username,`password`,company_personnel_id,enabled,salf FROM sys_user WHERE username=#{username}")
    AuthenticationUserDTO getUserByUserName(@Param("username") String username);

    /**
     * 获取用户对应系统的权限操作码
     */
    @Select("SELECT operate_code FROM \n" +
            "(SELECT srp.permission_id FROM\n" +
            "(SELECT role_id FROM (SELECT id FROM sys_user WHERE username=#{username}) su INNER JOIN " +
            "sys_user_role sur on sur.user_id=su.id) sr INNER JOIN sys_role_permission srp on srp.role_id=sr.role_id) srp " +
            "INNER JOIN sys_permission sp on sp.id=srp.permission_id AND system_id=#{systemId}")
    Set<String> getUserPermissionOperateCode(@Param("username")String username,@Param("systemId")int systemId);
}
