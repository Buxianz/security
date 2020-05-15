package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface OrganizationDAO {

    @Select("SELECT * FROM sys_organization WHERE organization_name = #{organizationName}")
    SysOrganization queryOrganizationInfoByOrganizationName(@Param("organizationName") String organizationName);

}
