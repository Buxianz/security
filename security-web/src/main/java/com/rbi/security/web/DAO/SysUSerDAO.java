package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysUser;
import org.apache.ibatis.annotations.*;


@Mapper
public interface SysUSerDAO {

    @Insert("insert into sys_user (username,password,salt,company_personnel_id,operating_staff,enabled,idt) values (#{username},#{password},#{salt},#{companyPersonnelId},#{operatingStaff},#{enabled},#{idt})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")//此语句代表添加后返回主键
    int insertUser(SysUser sysUser);

    @Select("Select * from sys_user where username=#{username}")
    SysUser increaseDuplicateCheck(@Param("username") String username);

    @Select("Select * from sys_user where company_personnel_id=#{company_personnel_id} and id!=#{id}")
    SysUser updateDuplicateCheck(SysUser sysUser);

    @Update("update sys_user set password=#{password},salf=#{salt},company_personnel_id=#{companyPersonnelId},enabled=#{enabled}")
    int updateUser(SysUser sysUser);

    @Delete("delete su.* from sys_user su where su.id=#{id}")
    int deleteUserById(@Param("id") int id);
}
