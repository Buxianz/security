package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.entity.web.permission.PagingPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface SysRolePermissionDAO {
    @Select("SELECT * FROM sys_role_permission WHERE role_id = #{roleId}")
    List<SysRolePermission> findSysRolePermissionByRoleId(@Param("roleId") Integer roleId);

    @Select("SELECT * FROM sys_role_permission WHERE id = #{id}")
    SysRolePermission findSysRolePermissionById(@Param("id") Integer id);

//    @Select("select sys_role_permission.* from sys_role_permission,sys_permission,sys_role " +
//            "WHERE sys_role.id=sys_role_permission.role_id AND sys_role_permission.permission_id = sys_permission.id " +
//            "ORDER BY sys_role_permission.id limit #{pageNo},#{pageSize}")
//    List<SysRolePermission> findSysRolePermissionByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
//    @Select("select count(sys_role_permission.id) from  sys_role_permission,sys_permission,sys_role WHERE " +
//            "sys_role.id=sys_role_permission.role_id AND sys_role_permission.permission_id = sys_permission.id ORDER BY sys_role_permission.id")
//    int findNumSysRolePermission();

    @Select("select * from sys_role_permission")
    List<SysRolePermission> findSysRolePermissionList();

    @Select("select * from sys_role_permission  limit #{pageNo},#{pageSize}")
    List<SysRolePermission> findSysRolePermissionByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from sys_role_permission")
    int findNumSysRolePermission();

    @Insert( "insert into sys_role_permission (permission_id,role_id) value(#{permissionId},#{roleId})")
    void insertSysRolePermission(@Param("permissionId") Integer permissionId, @Param("roleId") Integer roleId);

    @Update(value = "update sys_role_permission set permission_id=#{permissionId},role_id=#{roleId} where id=#{id}")
    void updateSysRolePermission(@Param("id") Integer id,@Param("permissionId") Integer permissionId,
                             @Param("roleId") Integer roleId);

    @Delete("delete from sys_role_permission where id=#{id}")
    void  deleteSysRolePermissionById(@Param("id") Integer id);
}
