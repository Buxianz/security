package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.user.HarmNameDTO;
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
    @Insert("insert into sys_user (username,password,salf,company_personnel_id,operating_staff,enabled,idt) values (#{username},#{password},#{salf},#{companyPersonnelId},#{operatingStaff},#{enabled},#{idt})")
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
    @Select("Select * from sys_user where company_personnel_id=#{companyPersonnelId} and id!=#{id}")
    SysUser updateDuplicateCheck(SysUser sysUser);

    /**
     * 更新数据
     * @param sysUser
     * @return
     */
    @Update("update sys_user set company_personnel_id=#{companyPersonnelId},enabled=#{enabled} where id=#{id}")
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
    @Select("SELECT su.*,scp.employee_number,scp.organization_id,scp.`name`,scp.id_card_no FROM\n" +
            "(SELECT id,username,company_personnel_id,enabled FROM sys_user) su LEFT JOIN sys_company_personnel scp ON scp.id=su.company_personnel_id ORDER BY id")
    List<PagingUser> getAllUserInfo();
    /**
     * 根据组织id，获取本身信息以及父级组织信息
     */
    @Select("SELECT\n" +
            "            T2.id,\n" +
            "            T2.organization_name,\n" +
            "            T2.parent_id,\n" +
            "            T2.`level`\n" +
            "            FROM\n" +
            "            (\n" +
            "           SELECT\n" +
            "            @r AS _id,\n" +
            "            (\n" +
            "            SELECT\n" +
            "            @r := parent_id\n" +
            "            FROM\n" +
            "            sys_organization\n" +
            "            WHERE\n" +
            "            id = _id\n" +
            "            ) AS parent_id,\n" +
            "            @l := @l + 1 AS lvl\n" +
            "            FROM\n" +
            "            (SELECT @r := #{id}, @l := 0) vars,\n" +
            "            sys_organization h\n" +
            "            WHERE\n" +
            "            @r <> 0\n" +
            "           ) T1\n" +
            "            JOIN sys_organization T2 ON T1._id = T2.id\n" +
            "            ORDER BY id")
    List<SysOrganization> getParentOrganization(@Param("id") int id);

    @Select("select * from sys_company_personnel where id = #{id}")
    SysCompanyPersonnel findById(Integer id);

    @Select("select * from sys_organization where id = #{id}")
    SysOrganization findAllByOrganizationId(@Param("id") int organizationId);

    @Select("select harm_name from risk_control where organization_id = #{organizationId}")
    List<HarmNameDTO> findHarmNameByOrganizationId(Integer organizationId);

    @Select("select harm_name from risk_control where work_type = #{workType}")
    List<HarmNameDTO> findHarmNameByWorkType(String workType);


    /**
     * 查询level为一的角色数量
     * */
    @Select("SELECT count(*) FROM sys_company_personnel,sys_user,sys_user_role,sys_role \n" +
            "WHERE sys_company_personnel.id = sys_user.company_personnel_id AND sys_user.id = sys_user_role.user_id \n" +
            "AND sys_user_role.role_id = sys_role.id\n" +
            "AND sys_role. LEVEL = 1\n" +
            "AND sys_company_personnel.organization_id = \n" +
            "(SELECT sys_organization.id FROM sys_organization,sys_company_personnel WHERE sys_organization.id = sys_company_personnel.organization_id AND sys_company_personnel.id = #{id})")
    int findFistLevelNum(@Param("id") Integer id);

    /**
     * 查询除了本身，level为一的角色数量
     * */
    @Select("SELECT count(*) FROM sys_company_personnel,sys_user,sys_user_role,sys_role \n" +
            "WHERE sys_company_personnel.id = sys_user.company_personnel_id AND sys_user.id = sys_user_role.user_id \n" +
            "AND sys_user_role.role_id = sys_role.id\n" +
            "AND sys_role. LEVEL = 1\n" +
            "AND sys_company_personnel.organization_id = \n" +
            "(SELECT sys_organization.id FROM sys_organization,sys_company_personnel WHERE sys_organization.id = sys_company_personnel.organization_id AND sys_company_personnel.id = #{id})")
    int findFistLevelNum2(@Param("id") Integer id);





}
