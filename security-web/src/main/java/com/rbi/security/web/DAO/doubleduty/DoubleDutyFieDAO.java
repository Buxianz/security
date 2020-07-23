package com.rbi.security.web.DAO.doubleduty;

import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluation;
import com.rbi.security.entity.web.doubleduty.DoubleDutyEvaluationContent;
import org.apache.ibatis.annotations.Mapper;
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
    @Select("select * from double_duty_evaluation limit #{pageNo},#{pageSize}")
    List<DoubleDutyEvaluation> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from double_duty_evaluation")
    int findByPageNum();

    @Select("select * from double_duty_evaluation_content where evaluation_id = #{evaluationId}")
    List<DoubleDutyEvaluationContent> findEvaluationContentById(Integer evaluationId);
}
