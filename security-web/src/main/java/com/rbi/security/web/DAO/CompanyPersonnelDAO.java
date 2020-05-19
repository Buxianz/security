package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.user.CompanyPersonnelBox;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyPersonnelDAO {

    @Insert("INSERT INTO sys_company_personnel (\n" +
            "\temployee_number,\n" +
            "\torganization_id,\n" +
            "\t`name`,\n" +
            "\tgender,\n" +
            "\tnation,\n" +
            "\tmarital_status,\n" +
            "\tid_card_no,\n" +
            "\tdate_of_birth,\n" +
            "\tdegree_of_education,\n" +
            "\tposition,\n" +
            "\tjob_nature,\n" +
            "\twork_type,\n" +
            "\tentry_time,\n" +
            "\tremarks,\n" +
            "\tidt\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{employeeNumber},\n" +
            "\t\t#{organizationId},\n" +
            "\t\t#{name},#{gender},\n" +
            "\t\t#{nation},\n" +
            "\t\t#{maritalStatus},\n" +
            "\t\t#{idCardNo},\n" +
            "\t\t#{dateOfBirth},\n" +
            "\t\t#{degreeOfEducation},\n" +
            "\t\t#{position},\n" +
            "\t\t#{jobNature},\n" +
            "\t\t#{workType},\n" +
            "\t\t#{entryTime},\n" +
            "\t\t#{remarks},\n" +
            "\t\t#{idt})")
    void insertCompanyPersonnelInfo(SysCompanyPersonnel sysCompanyPersonnel);

    @Update("UPDATE sys_company_personnel\n" +
            "SET\n" +
            "\temployee_number = #{employeeNumber},\n" +
            "\torganization_id = #{organizationId},\n" +
            "\t`name` = #{name},\n" +
            "\tgender = #{gender},\n" +
            "\tnation = #{nation},\n" +
            "\tmarital_status = #{maritalStatus},\n" +
            "\tid_card_no = #{idCardNo},\n" +
            "\tdate_of_birth = #{dateOfBirth},\n" +
            "\tdegree_of_education = #{degreeOfEducation},\n" +
            "\tposition = #{position},\n" +
            "\tjob_nature = #{jobNature},\n" +
            "\twork_type = #{workType},\n" +
            "\tentry_time = #{entryTime},\n" +
            "\tremarks = #{remarks},\n" +
            "\tudt = #{udt}\n" +
            "WHERE id = #{id}\n")
    void uodate(SysCompanyPersonnel sysCompanyPersonnel);

    @Delete("DELETE FROM sys_company_personnel WHERE id in (${ids})")
    void delete(@Param("ids") String ids);

    //根据员工号查询公司人员条数
    @Select("SELECT COUNT(*) FROM sys_company_personnel WHERE employee_number = #{employeeNumber}")
    int queryCountByEmployeeNumber(@Param("employeeNumber") String employeeNumber);

    //根据员工号和ID查询公司人员条数
    @Select("SELECT COUNT(*) FROM sys_company_personnel WHERE employee_number = #{employeeNumber} AND id<>#{id}")
    int queryCountByEmployeeNumberAndNotId(@Param("employeeNumber") String employeeNumber,@Param("id") long id);

    //根据身份证号查询公司人员数据条数
    @Select("SELECT COUNT(*) FROM sys_company_personnel WHERE id_card_no = #{idCardNo}")
    int queryCountByIdCardNo(@Param("idCardNo") String idCardNo);

    //根据身份证号和ID查询公司人员数据条数
    @Select("SELECT COUNT(*) FROM sys_company_personnel WHERE id_card_no = #{idCardNo} AND id<>#{id}")
    int queryCountByIdCardNoAndNotId(@Param("idCardNo") String idCardNo,@Param("id") long id);

    //分页查询公司人员数据
    @Select("SELECT sys_company_personnel.* FROM sys_company_personnel ${searchCriteria} LIMIT ${pageNo},${pageSize}")
    List<SysCompanyPersonnel> queryDataByPage(@Param("searchCriteria") String searchCriteria,
                                              @Param("pageNo") int pageNo,
                                              @Param("pageSize") int pageSize);

    //查询公司人员条数
    @Select("SELECT COUNT(*) FROM sys_company_personnel ${searchCriteria}")
    int queryCountByPage(@Param("searchCriteria") String searchCriteria);

    //获取公司所有人员信息
    @Select("select id,`name`,organization_id from sys_company_personnel")
    List<CompanyPersonnelBox> getAllCompanyPersonnel();
}
