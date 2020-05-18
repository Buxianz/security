package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysUserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleDAO {
    @Insert({
            "<script>",
            "insert into sys_user_role (user_id,role_id,operating_staff,idt) values ",
            "<foreach collection='sysUserRoleList' item='item' index='index' separator=','>",
            "(#{item.userId},#{item.roleId},#{operatingStaff},#{idt})",
            "</foreach>",
            "</script>"
    })
    int inserUserRoleInfo(@Param("sysUserRoleList") List<SysUserRole> sysUserRoleList);

}
