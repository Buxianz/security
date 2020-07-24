package com.rbi.security.web.DAO.doubleduty;

import com.rbi.security.entity.web.doubleduty.*;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysUserRole;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DoubleDutyTemplateDAO {

    @Select("select * from double_duty_template where personnel_id = #{personnelId} limit #{pageNo},#{pageSize}")
    List<DoubleDutyTemplate> findByPage(int personnelId,int pageNo, int pageSize);

    @Select("select count(*) from double_duty_template where personnel_id = #{personnelId}")
    int findByPageNum(int personnelId);

    @Insert({
            "<script>",
            "insert into double_duty_template_content (template_id,content,fraction) values ",
            "<foreach collection='doubleDutyTemplateContents' item='item' index='index' separator=','>",
            "(#{item.templateId},#{item.content},#{item.fraction})",
            "</foreach>",
            "</script>"
    })
    void addTemplateContent(@Param("doubleDutyTemplateContents")  List<DoubleDutyTemplateContent> doubleDutyTemplateContents);


    @Insert("insert into double_duty_template (position,personnel_id,idt,organization_id,organization_name,company_name,factory_name,workshop_name,class_name,name)values" +
            "(#{position},#{personnelId},#{idt},#{organizationId},#{organizationName},#{companyName},#{factoryName},#{workshopName},#{className},#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    void addTemplate(DoubleDutyTemplate doubleDutyTemplate);

    @Select("select name from sys_company_personnel where id = #{id}")
    String fidNameById(Integer personnelId);



    @Insert("insert into double_duty_evaluation (double_duty_id,personnel_id,name,status,idt) values (#{doubleDutyId},#{personnelId},#{name},#{status},#{idt})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    void addSingleEvaluation(DoubleDutyEvaluation doubleDutyEvaluation);

    @Update("update double_duty_template set name=#{name},position = #{position},personnel_id=#{personnelId},organization_id = #{organizationId}," +
            "organization_name=#{organizationName},company_name = #{companyName},factory_name=#{factoryName}," +
            "workshop_name = #{workshopName},class_name=#{className},udt=#{udt} where id = #{id}")
    void updateTemplate(DoubleDutyTemplate doubleDutyTemplate);

    @Delete("delete from double_duty_template_content where template_id = #{templateId}")
    void deleteTemplateContent(Integer templateId);

    @Delete("delete from double_duty_template where id = #{id}")
    void deleteTemplate(Integer id);

    @Select("select * from double_duty_template_content where template_id = #{templateId}")
    List<DoubleDutyTemplateContent> findDoubleDutyTemplateContentsBytemplateId(Integer templateId);

    @Insert("insert into double_duty_evaluation_content (evaluation_id,content_id)values(#{evaluationId},#{contentId}) ")
    void addEvaluationContent(Integer evaluationId, Integer contentId);

    @Select("select * from sys_organization where id = #{id}")
    SysOrganization findAllByOrganizationId(@Param("id") int organizationId);

    @Select("select count(*) from double_duty_template where organization_id=#{organizationId} and position=#{position}")
    int findAddNum(@Param("organizationId") Integer organizationId,@Param("position") String position);

    @Select("select count(*) from double_duty_template where organization_id=#{organizationId} and position=#{position} and id !=#{id}")
    int findUpdateNum(@Param("organizationId") Integer organizationId,@Param("position") String position, @Param("id") Integer id);
}
