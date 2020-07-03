package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccRegularMonitoring;
import com.rbi.security.entity.web.health.OccStatusEvaluation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.health
 * @NAME: OccStatusEvaluationDAO
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 17:07
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 17
 * @MINUTE: 07
 * @PROJECT_NAME: security
 **/
@Mapper
public interface OccStatusEvaluationDAO {

    @Select("select * from occ_status_evaluation order by id DESC limit #{pageNo},#{pageSize}")
    List<OccStatusEvaluation> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from occ_status_evaluation")
    int findByPageNum();

    @Insert("insert into occ_status_evaluation (time,evaluation_organization,evaluation_project,evaluation_result,annex,idt,operating_staff) values " +
            "(#{time},#{evaluationOrganization},#{evaluationProject},#{evaluationResult},#{annex},#{idt},#{operatingStaff})")
    void add(OccStatusEvaluation occStatusEvaluation);

    @Update("update occ_status_evaluation set time=#{time},evaluation_organization=#{evaluationOrganization},evaluation_project=#{evaluationProject}," +
            "evaluation_result=#{evaluationResult},annex = #{annex},udt=#{udt} where id = #{id}")
    void update(OccStatusEvaluation occStatusEvaluation);

    @Delete("delete from occ_status_evaluation where id = #{id}")
    void delete(int id);

    @Update("update occ_status_evaluation set annex = '' where id =#{id}")
    void updateAnnex(Integer id);

    @Select("select annex from occ_status_evaluation where id = #{id}")
    String findAnnex(Integer id);
}
