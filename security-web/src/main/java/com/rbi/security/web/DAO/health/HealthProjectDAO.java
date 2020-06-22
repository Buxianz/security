package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccHealthProject;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName HealthProjectDAO
 * @Description TODO
 * @Author muyizg
 * @Date 2020/6/21 14:32
 * @Version 1.0
 **/
@Mapper
public interface HealthProjectDAO {

    @Select("SELECT * FROM occ_health_project limit #{startIndex},#{pageSize}")
    List<OccHealthProject> getPageHealthPro(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);

    @Select("SELECT COUNT(id) FROM occ_health_project ")
    int getHealthCount();

    @Insert("INSERT INTO occ_health_project(\n" +
            "  health_project_name ,\n" +
            "  health_project_type ,\n" +
            "  health_project_investment,\n" +
            "  health_project_duration ,\n" +
            "  health_project_danger,\n" +
            "  health_project_evaluate_time,\n" +
            "  health_project_evaluate_organization,\n" +
            "  health_project_evaluate_conclusion,\n" +
            "  health_project_design_time,\n" +
            "  health_project_design_organization,\n" +
            "  health_project_design_conclusion,\n" +
            "  health_project_check_time,\n" +
            "  health_project_check_organization,\n" +
            "  health_project_check_conclusion,\n" +
            "  health_project_result_time,\n" +
            "  health_project_result_organization,\n" +
            "  health_project_result_conclusion) VALUES(#{healthProjectName},#{healthProjectType},#{healthProjectInvestment}," +
            "#{healthProjectDuration},#{healthProjectDanger},#{healthProjectEvaluateTime},#{healthProjectEvaluateOrganization},#{healthProjectEvaluateConclusion}," +
            "#{healthProjectDesignTime},#{healthProjectDesignOrganization},#{healthProjectDesignConclusion},#{healthProjectCheckTime},#{healthProjectCheckOrganization},#{healthProjectCheckConclusion}," +
            "#{healthProjectResultTime},#{healthProjectResultOrganization},#{healthProjectResultConclusion})")
    void insertHealthPro(OccHealthProject occHealthProject);

    @Select("SELECT health_project_name FROM occ_health_project WHERE health_project_name = #{healthProjectName}")
    String getOneHealthProByName(@Param("healthProjectName") String healthProjectName);

    @Select("SELECT health_project_name FROM occ_health_project WHERE id = #{id}")
    OccHealthProject getOneHealthProById(@Param("id") int id);

    @Update("UPDATE occ_health_project SET \n" +
            "             health_project_name = #{healthProjectName},\n" +
            "            health_project_type =#{healthProjectType},\n" +
            "           health_project_investment=#{healthProjectInvestment},\n" +
            "            health_project_duration= #{healthProjectDuration}, \n" +
            "             health_project_danger=#{healthProjectDanger},\n" +
            "            health_project_evaluate_time=#{healthProjectEvaluateTime},\n" +
            "            health_project_evaluate_organization=#{healthProjectEvaluateOrganization},\n" +
            "            health_project_evaluate_conclusion=#{healthProjectEvaluateConclusion},\n" +
            "            health_project_design_time=#{healthProjectDesignTime},\n" +
            "            health_project_design_organization=#{healthProjectDesignOrganization},\n" +
            "            health_project_design_conclusion=#{healthProjectDesignConclusion},\n" +
            "            health_project_check_time=#{healthProjectCheckTime},\n" +
            "           health_project_check_organization=#{healthProjectCheckOrganization},\n" +
            "           health_project_check_conclusion=#{healthProjectCheckConclusion},\n" +
            "           health_project_result_time=#{healthProjectResultTime},\n" +
            "            health_project_result_organization=#{healthProjectResultOrganization},\n" +
            "            health_project_result_conclusion=#{healthProjectResultConclusion} \n" +
            "            WHERE id = #{id}")
    void updateHealthPro(OccHealthProject occHealthProject);

    @Delete("DELETE FROM occ_health_project WHERE id = #{id}")
    void deleteHealthPro(@Param("id") int id);
}
