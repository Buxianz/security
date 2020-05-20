package com.rbi.security.web.DAO;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.organization.PagingOrganization;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface OrganizationDAO {
    /**
     * 根据组织名称获取组织信息
     * @param organizationName
     * @return
     */
    @Select("SELECT * FROM sys_organization WHERE organization_name = #{organizationName}")
    List<SysOrganization> queryOrganizationInfoByOrganizationName(@Param("organizationName") String organizationName);

    /**
     * 根据组织名称以及父级组织id获取组织信息
     * @param organizationName
     * @param organizationId
     * @return
     */
    @Select("SELECT * FROM sys_organization WHERE organization_name = #{organizationName} AND parent_id = #{parentId}")
    SysOrganization queryOrganizationInfoByOrganizationNameAndParentId(@Param("organizationName") String organizationName,
                                                                       @Param("parentId") long organizationId);

    /**
     * 子找父获取组织信息
     * @param organizationId
     * @return
     */
    @Select("SELECT\n" +
            "\tT2.id,\n" +
            "\tT2.organization_name,\n" +
            "\tT2.parent_id,\n" +
            "\tT2.`level`\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\t@r AS _id,\n" +
            "\t\t\t(\n" +
            "\t\t\t\tSELECT\n" +
            "\t\t\t\t\t@r := parent_id\n" +
            "\t\t\t\tFROM\n" +
            "\t\t\t\t\tsys_organization\n" +
            "\t\t\t\tWHERE\n" +
            "\t\t\t\t\tid = _id\n" +
            "\t\t\t) AS parent_id,\n" +
            "\t\t\t@l := @l + 1 AS lvl\n" +
            "\t\tFROM\n" +
            "\t\t\t(SELECT @r := #{id}, @l := 0) vars,\n" +
            "\t\t\tsys_organization h\n" +
            "\t\tWHERE\n" +
            "\t\t\t@r <> 0\n" +
            "\t) T1\n" +
            "JOIN sys_organization T2 ON T1._id = T2.id\n" +
            "ORDER BY\n" +
            "\tid;")
    List<SysOrganization> queryAllParentDate(@Param("id") long organizationId);

    /**
     * 获取所有组织信息
     * @return
     */
    @Select("select id,organization_name,parent_id,level from sys_organization")
    List<OrganizationTree> getAllOrganization();
    /**
     * 添加组织信息
     */
    @Insert("insert into sys_organization (organization_name,parent_id,level) values (#{organizationName},#{parentId},#{level})")
    int insertOrganization(SysOrganization sysOrganization);

    /**
     * 根据id查询组织信息
     */
    @Select("select * from sys_organization where id=#{id}")
    SysOrganization getOrganizationById(@Param("id") int id);
    /**
     * 更新组织信息
     */
    @Update("update sys_organization set organization_name={organizationName},parent_id=#{parentId},level=#{level} where id=#{id}")
    int updateOrganization(SysOrganization sysOrganization);

    /**
     * 分页查看组织信息
     */
    @Select("SELECT so1.*,so2.organization_name AS 'parent_name',so2.`level` AS 'parent_level' FROM \n" +
            "(SELECT * FROM sys_organization LIMIT #{startIndex},#{pageSize}) so1 LEFT JOIN sys_organization so2 ON so1.parent_id=so2.id")
    List<PagingOrganization> pagingOrganization(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 获取组织信息记录数
     */
    @Select("select count(id) from sys_organization")
    int getOrganizationCount();

}
