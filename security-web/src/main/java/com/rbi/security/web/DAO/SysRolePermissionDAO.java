package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.entity.web.role.RolePermissioonInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface SysRolePermissionDAO {
    @Select("select * from sys_role_permission")
    List<SysRolePermission> findSysRolePermissionList();




    /**吴松达******/
    /**
     * 批量添加角色权限关联
     * @param sysRolePermissions
     * @return
     */
    @Insert({
            "<script>",
            "insert into sys_role_permission (permission_id,role_id) values ",
            "<foreach collection='sysRolePermissions' item='item' index='index' separator=','>",
            "(#{item.permissionId},#{item.roleId})",
            "</foreach>",
            "</script>"
    })
    int insertRolePermissions(@Param("sysRolePermissions") List<SysRolePermission> sysRolePermissions);
    /**
     * 获取角色集合下面的权限
     */
    @Select({"<script>SELECT sp.*,ss.system_name FROM\n" +
            "(SELECT ssp.*,sp.permission_name,system_id,sp.parent_id,sp.enabled FROM\n" +
            "(SELECT * FROM sys_role_permission ssp WHERE ssp.role_id in <foreach collection='roleIds' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach>) ssp " +
            "INNER JOIN sys_permission sp ON sp.id=ssp.permission_id) sp INNER JOIN sys_system ss ON ss.id=sp.system_id </script> " })
    List<RolePermissioonInfo> getRolePermissionByRoleIds(@Param("roleIds") List<Integer> roleIds);
    /**
     * 根据角色id删除角色权限关联
     */
    @Delete("delete srp.* from sys_role_permission srp where role_id = #{roleId}")
    int deleteRolePermissionByRoleId(@Param("roleId") int roleId);

}
