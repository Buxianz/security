package com.rbi.security.web.DAO.doubleduty;

import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluation;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.hid.SystemSettingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.doubleduty
 * @NAME: DoubleDutyEvaluationDAO
 * @USER: "谢青"
 * @DATE: 2020/7/22
 * @TIME: 10:25
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 25
 * @PROJECT_NAME: security
 **/
@Mapper
public interface DoubleDutyEvaluationDAO {


    @Select("select * from double_duty_evaluation where personnel_id = #{personnelId} limit #{pageNo},#{pageSize}")
    List<DoubleDutyEvaluation> findPersonelByPage(Integer personnelId, Integer pageNo, Integer pageSize);

    @Select("select count(*) from double_duty_evaluation where personnel_id = #{personnelId}")
    int findPersonelNum(Integer personnelId);

    /**
     * 系统设置查询
     * */
    @Select("select setting_code,setting_name from system_setting where setting_type = #{settingType} and organization_id = 'RBI'")
    List<SystemSettingDTO> findChoose(String settingType);

    /**
     * personnel id查组织
     * */
    @Select("select * from sys_company_personnel where sys_company_personnel.id = #{id}")
    SysCompanyPersonnel findPersonnelById(@Param("id") Integer id);

    /**
     * userId查角色
     * */
    @Select("select * from sys_role where sys_role.id = (select role_id from sys_user_role where sys_user_role.user_id = #{userId})")
    SysRole findRoleByUserId(Integer userId);

    @Select("select * from double_duty_evaluation WHERE double_duty_evaluation.`status` = '2' and " +
            "(double_duty_evaluation.personnel_id IN (SELECT id from sys_company_personnel where sys_company_personnel.organization_id = #{organizationId}) OR \n" +
            "double_duty_evaluation.personnel_id IN (SELECT id from sys_company_personnel WHERE sys_company_personnel.organization_id IN " +
            "(SELECT id FROM sys_organization WHERE sys_organization.parent_id = #{organizationId}))) AND double_duty_evaluation.personnel_id != #{personnelId} limit #{pageNo},#{pageSize}")
    List<DoubleDutyEvaluation> findFirstLevelAuditByPage(@Param("organizationId") Integer organizationId,@Param("personnelId") Integer personnelId,
                                                         @Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    @Select("select count(*) from double_duty_evaluation WHERE double_duty_evaluation.`status` = '2' and " +
            "(double_duty_evaluation.personnel_id IN (SELECT id from sys_company_personnel where sys_company_personnel.organization_id = #{organizationId}) OR \n" +
            "double_duty_evaluation.personnel_id IN (SELECT id from sys_company_personnel WHERE sys_company_personnel.organization_id IN " +
            "(SELECT id FROM sys_organization WHERE sys_organization.parent_id = #{organizationId}))) AND double_duty_evaluation.personnel_id != #{personnelId}")
    int findFirstLevelAuditNum(@Param("organizationId") Integer organizationId,@Param("personnelId") Integer personnelId);


    @Select("select * from double_duty_evaluation WHERE double_duty_evaluation.`status` = '2' and " +
            "(double_duty_evaluation.personnel_id IN (SELECT id from sys_company_personnel where sys_company_personnel.organization_id = #{organizationId}) OR \n" +
            "double_duty_evaluation.personnel_id in (SELECT id from sys_company_personnel WHERE sys_company_personnel.organization_id IN " +
            "(SELECT id FROM sys_organization WHERE sys_organization.parent_id = #{organizationId}))) AND \n" +
            "double_duty_evaluation.personnel_id not IN (SELECT id FROM sys_company_personnel WHERE organization_id = #{organizationId} and id IN " +
            "(SELECT personnel_id from sys_user,sys_user_role,sys_role WHERE sys_user.id = sys_user_role.user_id and sys_user_role.role_id = sys_role.id and sys_role.`level` <= 2)) limit #{pageNo},#{pageSize}")
    List<DoubleDutyEvaluation> findSecondLevelAuditByPage(@Param("organizationId") Integer organizationId,
                                                          @Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    @Select("select count(*) from double_duty_evaluation WHERE double_duty_evaluation.`status` = '2' and " +
            "(double_duty_evaluation.personnel_id IN (SELECT id from sys_company_personnel where sys_company_personnel.organization_id = #{organizationId}) OR \n" +
            "double_duty_evaluation.personnel_id in (SELECT id from sys_company_personnel WHERE sys_company_personnel.organization_id IN " +
            "(SELECT id FROM sys_organization WHERE sys_organization.parent_id = 1))) AND \n" +
            "double_duty_evaluation.personnel_id not IN (SELECT id FROM sys_company_personnel WHERE organization_id = #{organizationId} and id IN " +
            "(SELECT personnel_id from sys_user,sys_user_role,sys_role WHERE sys_user.id = sys_user_role.user_id and sys_user_role.role_id = sys_role.id and sys_role.`level` <= 2))")
    int findSecondLevelAuditNum(Integer organizationId);

    @Update("update double_duty_evaluation set bad_situation = #{bad_situation},write_time=#{writeTime},status = #{status} where id = #{id}")
    void writeEvaluation(DoubleDutyEvaluation doubleDutyEvaluation);
}
