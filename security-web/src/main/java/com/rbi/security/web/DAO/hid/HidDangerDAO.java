package com.rbi.security.web.DAO.hid;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @USER: "谢青"
 * @DATE: 2020/5/21
 * @TIME: 17:45
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 45
 * @PROJECT_NAME: security
 **/
@Mapper
public interface HidDangerDAO {

    /**
     * userId查角色
     * */
    @Select("select * from sys_role where sys_role.id = (select role_id from sys_user_role where sys_user_role.user_id = #{userId})")
    SysRole findRoleByUserId(Integer userId);

    /**
     * 组织id找组织负责人id和name
     * */
    @Select("select sys_company_personnel.id,sys_company_personnel.name " +
            "from sys_company_personnel,sys_user,sys_user_role,sys_role where " +
            "sys_company_personnel.id = sys_user.company_personnel_id and sys_user.id = sys_user_role.user_id and " +
            "sys_user_role.role_id  =  sys_role.id and sys_role.level = 1 and organization_id = #{organizationId}")
    SysCompanyPersonnel findFirstUserByOrganizationId(Integer organizationId);

    /**
     * personnel id查组织
     * */
    @Select("select * from sys_company_personnel where sys_company_personnel.id = #{id}")
    SysCompanyPersonnel findPersonnelById(@Param("id") Integer id);

    /**
     * 组织id查组织
     * */
    @Select("select * from sys_organization where id = #{id}")
    SysOrganization findAllByOrganizationId(@Param("id") int organizationId);

    /**
     * 添加隐患内容
     * */
    @Insert("insert into hid_danger_process " +
            "(hid_danger_code,operator_id,operator_name,organization_id,organization_name,if_deal,deal_way,deal_time,idt," +
            "corrector_id,corrector_name,operator_organization_id,operator_organization_name)" +
            " values" +
            "(#{hidDangerCode},#{operatorId},#{operatorName},#{organizationId},#{organizationName},#{ifDeal},#{dealWay}," +
            "#{dealTime},#{idt},#{correctorId},#{correctorName},#{operatorOrganizationId},#{operatorOrganizationName})")
    void addProcess(HidDangerProcessDO hidDangerProcessDO);

    @Insert("insert into hid_danger_picture (hid_danger_code,before_picture) values (#{hidDangerCode},#{beforePicture})")
    void addBeforeImg(@Param("hidDangerCode") String hidDangerCode,@Param("beforePicture") String beforePicture);

    @Insert("insert into hid_danger_picture (hid_danger_code,after_picture) values (#{hidDangerCode},#{afterPicture})")
    void addAfterImg(@Param("hidDangerCode") String hidDangerCode,@Param("afterPicture") String afterPicture);

    /**
     * 添加隐患内容
     * */
    @Insert("insert into hid_danger (hid_danger_code,hid_danger_type,organization_id,organization_name,troubleshooting_time,hid_danger_content,hid_danger_grade,if_control_measures,if_rectification_plan," +
            "copy_organization_id,copy_organization_name,if_deal,governance_funds,completion_time,completion_situation,rectification_plan,acceptance_report,processing_status,idt," +
            "hid_type_thing,hid_type_person,hid_type_manage,corrector_id,corrector_name," +
            "rectification_notice_time,rectification_opinions,specified_rectification_time,rectification_notice_annex,rectification_unit_id,rectification_unit_name," +
            "company_id,company_name,factory_id,factory_name,workshop_id,workshop_name,class_id,class_name)values" +
            "(#{hidDangerCode},#{hidDangerType},#{organizationId},#{organizationName},#{troubleshootingTime},#{hidDangerContent},#{hidDangerGrade},#{ifControlMeasures},#{ifRectificationPlan}," +
            "#{copyOrganizationId},#{copyOrganizationName},#{ifDeal},#{governanceFunds},#{completionTime},#{completionSituation},#{rectificationPlan},#{acceptanceReport},#{processingStatus}," +
            "#{idt},#{hidTypeThing},#{hidTypePerson},#{hidTypeManage},#{correctorId},#{correctorName}," +
            "#{rectificationNoticeTime},#{rectificationOpinions},#{specifiedRectificationTime},#{rectificationNoticeAnnex},#{rectificationUnitId},#{rectificationUnitName}," +
            "#{companyId},#{companyName},#{factoryId},#{factoryName},#{workshopId},#{workshopName},#{classId},#{className})")
    void addHidDanger(HidDangerDO hidDangerDO);


    /**
    * 系统设置查询
    * */
    @Select("select setting_code,setting_name from system_setting where setting_type = #{settingType} and organization_id = 'RBI'")
    List<SystemSettingDTO> findChoose(String settingType);

    /**
     * 处理中的隐患分页查询
     * */
    @Select("select * from hid_danger where company_id = #{companyId} and processing_status !='5' limit #{pageNo},#{pageSize}")
    List<HidDangerDO> findAllDealHidByPage(@Param("companyId")Integer companyId,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);

    @Select("select count(*) from hid_danger where company_id = #{companyId} and processing_status !='5'")
    Integer findAllDealHidByPageNum(int companyId);

    @Select("SELECT * FROM hid_danger WHERE processing_status !='5' and hid_danger_code in " +
            "(SELECT hid_danger_code from hid_danger_process WHERE (operator_id = #{personnelId} or corrector_id = #{personnelId}) GROUP BY hid_danger_code) LIMIT #{pageNo},#{pageSize}")
    List<HidDangerDO> findPersonnelDealByPage(@Param("personnelId")Integer personnelId,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);

    @Select("SELECT count(*) FROM hid_danger WHERE processing_status !='5' and hid_danger_code in " +
            "(SELECT hid_danger_code from hid_danger_process WHERE (operator_id = #{personnelId} or corrector_id = #{personnelId}) GROUP BY hid_danger_code)")
    int findPersonnelDealByPageNum(@Param("personnelId")Integer personnelId);


    /**
     * 已完成得隐患分页查询
     * */
    @Select("select * from hid_danger where company_id = #{companyId} and processing_status ='5' limit #{pageNo},#{pageSize}")
    List<HidDangerDO> findAllFinishHidByPage(@Param("companyId")Integer companyId,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);

    @Select("select count(*) from hid_danger where company_id = #{companyId} and processing_status ='5'")
    Integer findAllFinishHidByPageNum(int companyId);

    @Select("SELECT * FROM hid_danger WHERE processing_status ='5' and hid_danger_code in " +
            "(SELECT hid_danger_code from hid_danger_process WHERE (operator_id = #{personnelId} or corrector_id = #{personnelId}) GROUP BY hid_danger_code) LIMIT #{pageNo},#{pageSize}")
    List<HidDangerDO> findPersonnelFinishByPage(@Param("personnelId")Integer personnelId,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);

    @Select("SELECT count(*) FROM hid_danger WHERE processing_status ='5' and hid_danger_code in " +
            "(SELECT hid_danger_code from hid_danger_process WHERE (operator_id = #{personnelId} or corrector_id = #{personnelId}) GROUP BY hid_danger_code)")
    int findPersonnelFinishByPageNum(@Param("personnelId")Integer personnelId);

    @Select("select * from hid_danger where hid_danger_code = #{hidDangerCode}")
    HidDangerDO findDetailByCode(String hidDangerCode);

    @Select("select * from hid_danger_picture where hid_danger_code = #{hidDangerCode} and before_picture !='' ")
    List<HidDangerPictureDO> findBeforPictureByHidDangerCode(String hidDangerCode);

    @Select("select * from hid_danger_picture where hid_danger_code = #{hidDangerCode} and after_picture !=''")
    List<HidDangerPictureDO> findAfterPictureByHidDangerCode(String hidDangerCode);


    @Select("select * from hid_danger_process where hid_danger_code = #{hidDangerCode} order by id")
    List<HidDangerProcessDO> findProcessByHidDangerCode(String hidDangerCode);


    @Update("update hid_danger set if_control_measures=#{ifControlMeasures}," +
            "if_rectification_plan=#{ifRectificationPlan}," +
            "if_deal=#{ifDeal},governance_funds=#{governanceFunds}," +
            "completion_time=#{completionTime},completion_situation=#{completionSituation}," +
            "rectification_plan=#{rectificationPlan},acceptance_report=#{acceptanceReport}," +
            "processing_status=#{processingStatus},corrector_id=#{correctorId}," +
            "corrector_name=#{correctorName} where hid_danger_code = #{hidDangerCode}")
    void updateCompleteHidDanger(HidDangerDO hidDangerDO);

    @Update("update hid_danger set processing_status = #{processingStatus},auditor_id=#{auditorId},auditor_name=#{auditorName}," +
            "audit_time=#{auditTime},rectification_evaluate=#{rectificationEvaluate} " +
            "where hid_danger_code = #{hidDangerCode}")
    void auditPass(HidDangerDO hidDangerDO);

    @Update("update hid_danger set processing_status = #{processingStatus},auditor_id=#{auditorId},auditor_name=#{auditorName}," +
            "audit_time=#{auditTime},rectification_evaluate=#{rectificationEvaluate},if_deal='否',governance_funds = null," +
            "completion_time = null,completion_situation=null,rectification_plan =null,acceptance_report=null," +
            "if_control_measures = '无',if_rectification_plan = '无' where hid_danger_code = #{hidDangerCode}")
    void auditFalse(HidDangerDO hidDangerDO);


    /**
     * 通知整改，修改信息
     * */
    @Update("update hid_danger set " +
            "processing_status = #{processingStatus},rectification_opinions = #{rectificationOpinions}," +
            "corrector_id = #{correctorId},corrector_name = #{correctorName}," +
            "rectification_notice_time = #{rectificationNoticeTime},specified_rectification_time = #{specifiedRectificationTime}," +
            "if_deal='否',governance_funds = null,completion_time = null,completion_situation=null,rectification_plan =null,acceptance_report=null," +
            "if_control_measures = '无',if_rectification_plan = '无' where hid_danger_code = #{hidDangerCode}")
    void updateNotice(HidDangerDO hidDangerDO);


    @Select("select sys_company_personnel.id as settingCode,sys_company_personnel.name as settingName from sys_company_personnel,sys_user where sys_company_personnel.id = sys_user.company_personnel_id and organization_id = #{organizationId} and " +
            "sys_company_personnel.id !=#{personnelId}")
    List<SysPersonnelDTO> findPersonnelByOrganizationId(@Param("organizationId")Integer organizationId,@Param("personnelId")Integer personnelId);

    @Select("select sys_company_personnel.id as settingCode,sys_company_personnel.name as settingName from sys_company_personnel,sys_user where sys_company_personnel.id = sys_user.company_personnel_id and organization_id = #{organizationId} and " +
            "sys_company_personnel.id !=#{personnelId} and " +
            "sys_company_personnel.id !=((select sys_company_personnel.id from sys_company_personnel,sys_user,sys_user_role,sys_role where" +
            " sys_company_personnel.id = sys_user.company_personnel_id and sys_user.id = sys_user_role.user_id and " +
            "sys_user_role.role_id  =  sys_role.id and sys_role.level = 1 and organization_id = #{organizationId}))")
    List<SysPersonnelDTO> findPersonnelByOrganizationId2(@Param("organizationId")Integer organizationId,@Param("personnelId")Integer personnelId);


    /**
     * 组织id找组织负责人id和name
     * */
    @Select("select sys_company_personnel.id as settingCode,sys_company_personnel.name as settingName " +
            "from sys_company_personnel,sys_user,sys_user_role,sys_role where " +
            "sys_company_personnel.id = sys_user.company_personnel_id and sys_user.id = sys_user_role.user_id and " +
            "sys_user_role.role_id  =  sys_role.id and sys_role.level = 1 and organization_id in (select id from sys_organization where parent_id = #{organizationId})")
    List<SysPersonnelDTO> findAllFirstUserByOrganizationId(Integer organizationId);


    @Update("update hid_danger set " +
            "troubleshooting_time=#{troubleshootingTime},hid_danger_content=#{hidDangerContent}," +
            "hid_danger_grade=#{hidDangerGrade},if_control_measures=#{ifControlMeasures}," +
            "if_rectification_plan=#{ifRectificationPlan}," +
            "governance_funds=#{governanceFunds},hid_type_thing=#{hidTypeThing}," +
            "hid_type_person=#{hidTypePerson},hid_type_manage=#{hidTypeManage} " +
            "where hid_danger_code = #{hidDangerCode}")
    void reportHidDanger(HidDangerDO hidDangerDO);


    @Select("select * from hid_danger where hid_danger_code = #{hidDangerCode}")
    HidDangerDO findAllByHidDangerCode(String hidDangerCode);

    @Select("SELECT * from hid_danger_process WHERE hid_danger_code = #{hidDangerCode} and id = (select MAX(id) from hid_danger_process where hid_danger_code = #{hidDangerCode})")
    HidDangerProcessDO findLastProcess(String hidDangerCode);

    @Delete("delete from hid_danger_picture where hid_danger_code = #{hidDangerCode} and after_picture != ''")
    void deleteAfterPictureByHidDangerCode(String hidDangerCode);

    @Update("update hid_danger set rectification_plan = '' where hid_danger_code = #{hidDangerCode}")
    void deletePlan(String hidDangerCode);


    @Update("update hid_danger set acceptance_report = '' where hid_danger_code = #{hidDangerCode}")
    void deleteReport(String hidDangerCode);

    @Delete("delete from hid_danger_picture where id = #{id}")
    void deletePicture(Integer id);

    @Update("update hid_danger set if_control_measures = '无',if_rectification_plan = '无' where hid_danger_code = #{hidDangerCode}")
    void updateIf(String hidDangerCode);

    @Update("update hid_danger set processing_status = #{processingStatus} where hid_danger_code = #{hidDangerCode}")
    void updateProcessingStatus(@Param("processingStatus") String processingStatus,@Param("hidDangerCode")String hidDangerCode);
}
