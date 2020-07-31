package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.entity.SafeSubject;
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
    @Insert("insert into safe_test_questions (subject,subject_type,right_key,test_papre_id,score,question_bank_subject_id) values (#{subject},#{subjectType},#{rightKey},#{testPapreId},#{score},#{questionBankSubjectId})")
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

    /**
     * 获取时间开始与结束时间
     */
    @Select("SELECT CONCAT(start_time,\"至\",end_time) FROM safe_test_paper WHERE training_plan_id=#{trainingPlanId}")
    String getStartAndEndTime(@Param("trainingPlanId")int trainingPlanId);


    /**
     * 根据计划id获取试卷题目的信息 题库的题目id，题目类型 题目题库id
     */
    @Select("SELECT ss.* FROM\n" +
            "(SELECT stq.id,stq.question_bank_subject_id FROM\n" +
            "(SELECT id  FROM safe_test_paper WHERE training_plan_id=#{trainingPlanId})tp inner join safe_test_questions stq on\n" +
            "stq.test_papre_id=tp.id) stq INNER join safe_subject ss on ss.id=stq.question_bank_subject_id")
    List<SafeSubject> getTestPaperSubjectByPlanId(@Param("trainingPlanId")int trainingPlanId);
}
