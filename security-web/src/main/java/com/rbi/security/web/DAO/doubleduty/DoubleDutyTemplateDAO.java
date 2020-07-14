package com.rbi.security.web.DAO.doubleduty;

import com.rbi.security.entity.web.doubleduty.*;
import com.rbi.security.entity.web.entity.SysUserRole;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DoubleDutyTemplateDAO {

    @Select("")
    List<OccDiseaseProtection> findByPage(int pageNo2, int pageSize);

    @Select("")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int findByPageNum();

    @Insert({
            "<script>",
            "insert into double_duty_template_content (template_id,content,fraction) values ",
            "<foreach collection='doubleDutyTemplateContents' item='item' index='index' separator=','>",
            "(#{item.templateId},#{item.content},#{item.fraction})",
            "</foreach>",
            "</script>"
    })
    void addTemplateContent(@Param("doubleDutyTemplateContents")  List<DoubleDutyTemplateContent> doubleDutyTemplateContents);


    @Insert("insert into double_duty_template (job,personnel_id,idt)values(#{job},#{personnelId},#{idt})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    void addTemplate(DoubleDutyTemplate doubleDutyTemplate);

    @Select("select name from sys_company_personnel where id = #{id}")
    String fidNameById(Integer personnelId);


    @Insert("insert into double_duty (job,personnel_id,name,idt)values(#{job},#{personnelId},#{name},#{idt})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    void addDuty(DoubleDuty doubleDuty);

    @Insert({
            "<script>",
            "insert into double_duty_content (double_duty_id,content,fraction) values ",
            "<foreach collection='doubleDutyContents' item='item' index='index' separator=','>",
            "(#{item.doubleDutyId},#{item.content},#{item.fraction})",
            "</foreach>",
            "</script>"
    })
    void addContent(@Param("doubleDutyContents") List<DoubleDutyContent> doubleDutyContents);


    @Insert({
            "<script>",
            "insert into double_duty_evaluation (double_duty_id,personnel_id,name,status,idt) values ",
            "<foreach collection='doubleDutyEvaluations' item='item' index='index' separator=','>",
            "(#{item.doubleDutyId},#{item.personnelId},#{item.name},#{item.status},#{item.idt})",
            "</foreach>",
            "</script>"
    })
    void addEvaluation(@Param("doubleDutyEvaluations") List<DoubleDutyEvaluation> doubleDutyEvaluations);
}
