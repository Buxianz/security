package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.entity.SysUserRole;
import com.rbi.security.entity.web.role.PagingRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Mapper
public interface SysRoleDAO {
    /**
     * 获取所有角色
     * @return
     */
    @Select("select * from sys_role")
    List<SysRole> getALlRole();
    /**
     * 添加角色
     */
    @Insert("insert into sys_role (role_name,whether_see,level,enabled,operating_staff) values (#{roleName},#{whetherSee},#{level},#{enabled},#{operatingStaff})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertRole(SysRole sysRole);
    /**
     * 根据名称获取角色信息
     */
    @Select("select * from sys_role where role_name=#{roleName}")
    SysRole getRoleByName(@Param("roleName") String roleName);

    /**
     * 获取角色记录数
     */
    @Select("SELECT COUNT(id) from sys_role")
    int getRoleCount();

    /**
     * 分页获取角色信息
     */
    @Select("SELECT id,role_name,whether_see,level,enabled from sys_role LIMIT #{startIndex},#{pageSize}")
    List<PagingRole> pagingRole(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 更新角色信息查重
     */
    @Select("select * from sys_role where role_name=#{roleName} and id!=#{id}")
     SysRole updateDuplicateJudgement(SysRole sysRole);
    /**
     * 更新角色信息
     */
    @Update("update sys_role set role_name=#{roleName},level=#{level},enabled=#{enabled},whether_see=#{whetherSee} where id=#{id}")
    public int updateRoleById(SysRole sysRole);

    /**
     * 根据id查询角色信息
     */
    @Select("select * from sys_role where  id=#{id}")
    SysRole getRoleId(@Param("id") int id);
}
