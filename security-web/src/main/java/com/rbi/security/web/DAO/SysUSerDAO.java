package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.user.PagingUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SysUSerDAO {

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @Insert("insert into sys_user (username,password,salt,company_personnel_id,operating_staff,enabled,idt) values (#{username},#{password},#{salt},#{companyPersonnelId},#{operatingStaff},#{enabled},#{idt})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")//此语句代表添加后返回主键
    int insertUser(SysUser sysUser);

    /**
     * 添加数据前排重
     * @param username
     * @return
     */
    @Select("Select * from sys_user where username=#{username}")
    SysUser increaseDuplicateCheck(@Param("username") String username);

    /**
     * 更新前排重
     * @param sysUser
     * @return
     */
    @Select("Select * from sys_user where company_personnel_id=#{company_personnel_id} and id!=#{id}")
    SysUser updateDuplicateCheck(SysUser sysUser);

    /**
     * 更新数据
     * @param sysUser
     * @return
     */
    @Update("update sys_user set password=#{password},salf=#{salt},company_personnel_id=#{companyPersonnelId},enabled=#{enabled}")
    int updateUser(SysUser sysUser);

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Delete("delete su.* from sys_user su where su.id=#{id}")
    int deleteUserById(@Param("id") int id);
    /**
     * 获取用户数量
     */
    @Select("Select count(id) from sys_user")
    int getUserCount();
    /*
    分页获取用户数据
     */
    @Select("Select  from sys_user")
    List<PagingUser> pagingUserInfo(@Param("startIndex") int startIndex,@Param("pageSize")int pageSize);


}
