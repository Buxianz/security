package com.rbi.security.web.DAO.hid;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.HidDangerDTO;
import com.rbi.security.entity.web.hid.HidDangerProcessDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HidDangerDAO {

    @Select("select * from sys_company_personnel where sys_company_personnel.id = (select company_personnel_id from sys_user where sys_user.id = #{userId})")
    SysCompanyPersonnel findPersonnelByUserId(@Param("userId") Integer userId);

    @Select("select * from sys_role where sys_role.id = (select role_id from sys_user_role where sys_user_role.user_id = #{userId})")
    SysRole findRoleByUserId(Integer userId);

    @Insert("insert into hid_danger_picture (hid_danger_code,before_picture) values (#{hidDangerCode},#{beforePicture})")
    void addBeforeImg(@Param("hidDangerCode") String hidDangerCode,@Param("beforePicture") String beforePicture);

    @Insert("insert into hid_danger_picture (hid_danger_code,after_picture) values (#{hidDangerCode},#{afterPicture})")
    void addAfterImg(@Param("hidDangerCode") String hidDangerCode,@Param("afterPicture") String afterPicture);

    @Select("select * from sys_organization where id = #{id}")
    SysOrganization findAllByOrganizationId(@Param("id") int organizationId);

    //***********开始：
    @Insert("insert into hid_danger_process (hid_danger_code,operator_id,operator_name,organization_id,organization_name,if_deal,deal_way,deal_time,idt) values" +
            "(#{hidDangerCode},#{operatorId},#{operatorName},#{organizationId},#{organizationName},#{ifDeal},#{dealWay},#{dealTime},#{idt})")
    void addProcess(HidDangerProcessDTO hidDangerProcessDTO);

    //***********开始：
    @Insert("insert into hid_danger (hid_danger_code,hid_danger_type,organization_id,organization_name,troubleshooting_time,hid_danger_content,hid_danger_grade,if_control_measures,if_rectification_plan," +
            "copy_organization_id,copy_organization_name,if_deal,governance_funds,completion_time,completion_situation,rectification_plan,acceptance_report,processing_status,idt)values" +
            "(#{hidDangerCode},#{hidDangerType},#{organizationId},#{organizationName},#{troubleshootingTime},#{hidDangerContent},#{hidDangerGrade},#{ifControlMeasures},#{ifRectificationPlan}," +
            "#{copyOrganizationId},#{copyOrganizationName},#{ifDeal},#{governanceFunds},#{completionTime},#{completionSituation},#{rectificationPlan},#{acceptanceReport},#{processingStatus}," +
            "#{idt})")
    void addHidDanger(HidDangerDTO hidDangerDTO);

    @Insert("insert into hid_danger_organization (hid_danger_code,organization_id,organization_name,level) values" +
            "(#{hidDangerCode},#{organizationId},#{organizationName},#{level})")
    void addOrganization(@Param("hidDangerCode") String hidDangerCode,@Param("organizationId") Integer organizationId,
                         @Param("organizationName") String organizationName,@Param("level") Integer level);
}
