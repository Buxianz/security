package com.rbi.security.web.DAO.doubleduty;

import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluation;
import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluationContent;
import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.doubleduty
 * @NAME: DoubleDutyFieDAO
 * @USER: "谢青"
 * @DATE: 2020/7/21
 * @TIME: 11:48
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 11
 * @MINUTE: 48
 * @PROJECT_NAME: security
 **/
@Mapper
public interface DoubleDutyFieDAO {
    @Select("select * from double_duty_evaluation where status = '3' and (double_duty_evaluation.organization_id in (select id from (\n" +
            "select t1.id,\n" +
            "if(find_in_set(parent_id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "from (\n" +
            "select id,parent_id from sys_organization t order by parent_id, id\n" +
            ") t1,\n" +
            "(select @pids := #{organizationId}) t2\n" +
            ") t3 where ischild != 0) or double_duty_evaluation.organization_id = #{organizationId}) " +
            "order by double_duty_evaluation.id DESC limit #{pageNo},#{pageSize}")
    List<DoubleDutyEvaluation> findByPage(@Param("organizationId")Integer organizationId, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @Select("select count(*) from double_duty_evaluation where status = '3' and (double_duty_evaluation.organization_id in (select id from (\n" +
            "select t1.id,\n" +
            "if(find_in_set(parent_id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "from (\n" +
            "select id,parent_id from sys_organization t order by parent_id, id\n" +
            ") t1,\n" +
            "(select @pids := #{organizationId}) t2\n" +
            ") t3 where ischild != 0) or double_duty_evaluation.organization_id = #{organizationId})")
    int findByPageNum(@Param("organizationId")Integer organizationId);


    @Select("select * from double_duty_evaluation_content where evaluation_id = #{evaluationId}")
    List<DoubleDutyEvaluationContent> findEvaluationContentById(Integer evaluationId);

    @Select("select * from sys_company_personnel where id = #{id}")
    SysCompanyPersonnel findPersonnelById(Integer personnelId);
}
