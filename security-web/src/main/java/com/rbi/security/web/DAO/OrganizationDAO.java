package com.rbi.security.web.DAO;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface OrganizationDAO {

    @Select("SELECT * FROM sys_organization WHERE organization_name = #{organizationName}")
    List<SysOrganization> queryOrganizationInfoByOrganizationName(@Param("organizationName") String organizationName);

    @Select("SELECT * FROM sys_organization WHERE organization_name = #{organizationName} AND parent_id = #{parentId}")
    SysOrganization queryOrganizationInfoByOrganizationNameAndParentId(@Param("organizationName") String organizationName,
                                                                       @Param("parentId") long organizationId);

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

    @Select("select id,organization_name,parent_id from sys_organization")
    List<OrganizationTree> getAllOrganization();
}
