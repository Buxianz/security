package com.rbi.security.web.DAO;

import com.rbi.security.entity.config.PermissionTreeInfo;
import com.rbi.security.entity.web.entity.SysPermission;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysPermissionDAO{
    /**
     * 获取全部权限
     */
    @Select("SELECT * FROM sys_permission")
    List<SysPermission> findSysPermissionAll();

    /**
     * 根据id获取权限
     */
    @Select("SELECT * FROM sys_permission WHERE id = #{id}")
    SysPermission findSysPermissionById(@Param("id") Integer id);

    /**
     * 分页获取权限
     */
    @Select("select * from sys_permission ORDER BY idt DESC limit #{pageNo},#{pageSize}")
    List<SysPermission> findSysPermissionByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from sys_permission")
    int findNumSysPermission();

    /**
     * 添加权限
     */
    @Insert( "insert into sys_permission (permission_name,operate_code,parent_id,description,system_id,enabled,idt,udt) value(#{permissionName}," +
            "#{operateCode},#{parentId},#{description},#{systemId},#{enabled},#{idt},#{udt})")
    void insertSysPermission(@Param("permissionName") String permissionName, @Param("operateCode") String operateCode,
                    @Param("parentId") Integer parentId, @Param("description") String description,
                    @Param("systemId") Integer systemId, @Param("enabled") Integer enabled,
                    @Param("idt") String idt, @Param("udt") String udt);

    /**
     * 根据id修改权限
     */
    @Update(value = "update sys_permission set permission_name=#{permissionName},operate_code=#{operateCode},parent_id=#{parentId},description=#{description}," +
            "system_id=#{systemId},enabled=#{enabled},udt=#{udt} where id=#{id}")
    void updateSysPermission(@Param("id") Integer id,@Param("permissionName") String permissionName,
                    @Param("operateCode") String operateCode,@Param("parentId") Integer parentId,
                    @Param("description") String description,@Param("systemId") Integer systemId,
                    @Param("enabled") Integer enabled,@Param("udt") String udt);

    /**
     * 根据id删除权限
     */
    @Delete("delete sp.* from sys_permission sp where sp.id=#{id}")
    void  deleteSysPermissionById(@Param("id") Integer id);

    /****************吴松达******************/
    /**
     * 获取平台的全部权限
     */
    @Select("select * from sys_permission")
    List<PermissionTreeInfo> getAllSsytemPermission();
}
