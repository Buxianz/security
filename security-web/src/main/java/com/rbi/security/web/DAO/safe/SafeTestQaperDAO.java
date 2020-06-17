package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestions;
import com.rbi.security.entity.web.safe.testpaper.TestPaper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface SafeTestQaperDAO {
    /**
     * 添加试卷
     */
    @Insert("insert into safe_test_paper (start_time,end_time,duration,training_plan_id,test_paper_name,exam_notes) values (#{startTime},#{endTime},#{duration}," +
            "#{trainingPlanId},#{testPaperName},#{examNotes})")
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
    /**
     * 获取试卷基本信息
     */
    @Select("SELECT * FROM safe_test_paper where id=#{id}")
    TestPaper getTestPaper(@Param("id") int id);
    /**
     * 获取试卷题目内容
      */
    @Select("SELECT * FROM safe_test_questions WHERE test_papre_id=#{id}")
    List<SafeTestQuestions> getTestQuestions(@Param("id")int id);
    /**
     * 获取题目的选项
     */
    @Select({"<script> select * from safe_test_question_options where subject_id in\n"+
            "<foreach collection='testQuestionsIds' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach>\n"+
            "</script>"})
    List<SafeTestQuestionOptions> getTestQuestionOptions(@Param("testQuestionsIds")List<Integer> testQuestionsIds);
}
