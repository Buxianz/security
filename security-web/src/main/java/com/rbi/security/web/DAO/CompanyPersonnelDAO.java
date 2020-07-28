package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.user.CompanyPersonnelBox;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyPersonnelDAO {

    /**
     * 生成查询机构所有父级的函数
     * DROP FUNCTION IF EXISTS getOrganizationParentList
     * CREATE FUNCTION `getOrganizationParentList`(rootId INT)
     * RETURNS varchar(1000)
     * BEGIN
     *     DECLARE sTemp VARCHAR(1000);
     *     DECLARE sTempPar VARCHAR(1000);
     *     SET sTemp = '';
     *     SET sTempPar = rootId;
     *
     *     #循环递归
     *     WHILE sTempPar is not null DO
     *         #判断是否是第一个，不加的话第一个会为空
     *         IF sTemp != '' THEN
     *             SET sTemp = concat(sTemp,',',sTempPar);
     *         ELSE
     *             SET sTemp = sTempPar;
     *         END IF;
     *         SET sTemp = concat(sTemp,',',sTempPar);
     *         SELECT group_concat(parent_id) INTO sTempPar FROM sys_organization where parent_id<>id and FIND_IN_SET(id,sTempPar)>0;
     *     END WHILE;
     *
     * RETURN sTemp;
     * END
     *
     * select * from sys_organization where FIND_IN_SET(id,getOrganizationParentList(2));

     //*
     * 生成查询组织所有子节点的函数
     *drop FUNCTION if exists getOrganizationChildList;
     * CREATE FUNCTION `getOrganizationChildList`(rootId INT)
     * RETURNS varchar(1000)
     *
     * BEGIN
     *     DECLARE sTemp VARCHAR(1000);
     *     DECLARE sTempChd VARCHAR(1000);
     *
     *     SET sTemp = '$';
     *     SET sTempChd =cast(rootId as CHAR);
     *
     *     WHILE sTempChd is not null DO
     *         SET sTemp = concat(sTemp,',',sTempChd);
     *         SELECT group_concat(id) INTO sTempChd FROM sys_organization where FIND_IN_SET(parent_id,sTempChd)>0;
     *     END WHILE;
     *     RETURN sTemp;
     * END
     * select * from sys_organization where FIND_IN_SET(id,getOrganizationChildList(2));
     * @param sysCompanyPersonnel
     */

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

    //根据身份证号查询公司人员数据条数
    @Select("SELECT id FROM sys_company_personnel WHERE id_card_no = #{idCardNo}")
    Integer getPersonnelByIdCardNo(@Param("idCardNo") String idCardNo);

    @Select("SELECT * FROM sys_company_personnel WHERE id_card_no = #{idCardNo}")
    SysCompanyPersonnel getPersonnel(@Param("idCardNo") String idCardNo);
    //根据身份证号和ID查询公司人员数据条数
    @Select("SELECT COUNT(*) FROM sys_company_personnel WHERE id_card_no = #{idCardNo} AND id<>#{id}")
    int queryCountByIdCardNoAndNotId(@Param("idCardNo") String idCardNo,@Param("id") long id);

    //分页查询公司人员数据
    @Select("(SELECT scp.* FROM \n" +
            "(select id as 'organization_id'  from (\n" +
            "                          select t1.id,\n" +
            "                          if(find_in_set(parent_id, @pids) > 0 OR find_in_set(id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "                        from (\n" +
            "                               select id,parent_id from sys_organization \n" +
            "                             ) t1,\n" +
            "                              (select @pids := #{organizationId}) t2\n" +
            "            ) t3 where ischild != 0) t4 INNER JOIN (SELECT sys_company_personnel.* FROM sys_company_personnel ${searchCriteria}) scp on scp.organization_id=t4.organization_id)LIMIT ${pageNo},${pageSize}")
    List<SysCompanyPersonnel> queryDataByPage(@Param("searchCriteria") String searchCriteria,
                                              @Param("pageNo") int pageNo,
                                              @Param("pageSize") int pageSize,@Param("organizationId") int organizationId);

    //查询公司人员条数
    @Select("(SELECT COUNT(scp.id) FROM \\n\" +\n" +
            "            \"(select id as 'organization_id'  from (\\n\" +\n" +
            "            \"                          select t1.id,\\n\" +\n" +
            "            \"                          if(find_in_set(parent_id, @pids) > 0 OR find_in_set(id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\\n\" +\n" +
            "            \"                        from (\\n\" +\n" +
            "            \"                               select id,parent_id from sys_organization \\n\" +\n" +
            "            \"                             ) t1,\\n\" +\n" +
            "            \"                              (select @pids := #{organizationId}) t2\\n\" +\n" +
            "            \"            ) t3 where ischild != 0) t4 INNER JOIN (SELECT sys_company_personnel.* FROM sys_company_personnel ${searchCriteria}) scp on scp.organization_id=t4.organization_id)")
    int queryCountByPage(@Param("searchCriteria") String searchCriteria);

    //获取公司所有人员信息
    @Select("select id,`name`,organization_id from sys_company_personnel")
    List<CompanyPersonnelBox> getAllCompanyPersonnel();

    /*****吴松达****/
    /**
     * 获取集合内id在表中的数量
     */

    @Select({"<script> Select count(id) FROM sys_company_personnel WHERE id in <foreach collection='targets' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach> </script>"})
    int getCompanyPersonneCountByIds(@Param("targets")List<Integer> targets);
    /**
     * 获取id集合对应的名称
     */
    @Select("SELECT `name` FROM sys_company_personnel where id in ${targetSet}")
    String[] getCompanyPersonneName(@Param("targetSet") String targetSet);
    /**
     * 根据身份证集合获取 信息
     */
    @Select({"<script> Select * FROM sys_company_personnel WHERE id_card_no in <foreach collection='idCardNos' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach> </script>"})
    List<SysCompanyPersonnel> getCompanyPersonnelByIdCardNos(@Param("idCardNos") List<String> idCardNos);

    /**
     * 根据公司人员信息id获取其所在的组织id
     */
    @Select("Select organization_id FROM sys_company_personnel WHERE id=#{companyPersonnelId}")
    Integer  getorganizationIdById(@Param("companyPersonnelId") int companyPersonnelId);

}
