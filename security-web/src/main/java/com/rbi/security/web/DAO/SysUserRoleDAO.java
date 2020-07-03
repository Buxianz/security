package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.entity.SysUserRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface SysUserRoleDAO {
    @Insert({
            "<script>",
            "insert into sys_user_role (user_id,role_id,operating_staff,idt) values ",
            "<foreach collection='sysUserRoleList' item='item' index='index' separator=','>",
            "(#{item.userId},#{item.roleId},#{item.operatingStaff},#{item.idt})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int inserUserRoleInfo(@Param("sysUserRoleList") List<SysUserRole> sysUserRoleList);
   @Select({"<script> SELECT sur.*,sr.role_name FROM\n" +
           "(select id,role_id,user_id FROM sys_user_role WHERE user_id in <foreach collection='userIds' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach>) sur INNER JOIN sys_role sr ON sr.id=sur.role_id</script>"})
    List<SysUserRole> getUserRole(@Param("userIds") List<Integer> userIds);

    @Update({"<script><foreach collection='sysUserRoleList' item='item' index='index' separator=';'>\n" +
            "    update sys_user_role " +
            "    <set>" +
            "      <if test='item.roleId != null'>" +
            "        role_id = #{item.roleId} " +
            "      </if>" +
            "    </set>" +
            "    where user_id = #{item.userId} and id=#{item.id}" +
            "  </foreach></script>"})
    int updateUserRoleInfo(@Param("sysUserRoleList") List<SysUserRole> sysUserRoleList);


    /**
     * 根据角色id获取所有信息
     */
    @Select("select * from sys_user_role where role_id=#{roleId}")
    List<SysUserRole> getSysUserRoles(@Param("roleId")int roleId);
}
