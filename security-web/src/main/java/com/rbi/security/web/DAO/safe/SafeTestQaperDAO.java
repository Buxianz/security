package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SafeTestQaperDAO {
    /**
     * 添加试卷
     */
    @Insert("insert into safe_test_paper (start_time,end_time,duration,training_plan_id) values (#{startTime},#{endTime},#{duration}," +
            "#{trainingPlanId})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeTestPaper(SafeTestPaper safeTestPaper);

    /**
     * 添加题目
     */
    @Insert("insert into safe_test_questions (subject,subject_type,right_key,test_papre_id,score) values (#{subject},#{subjectType},#{rightKey},#{testPapreId},#{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeTestQuestions(SafeTestQuestions safeTestQuestions);

    /**
     * 添加选项
     */
    @Insert("insert into safe_test_question_options (subject_id,`option`,`order`) values (#{subjectId},#{option},#{order})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeTestQuestionOptions(SafeTestQuestionOptions safeTestQuestionOptions);
}
