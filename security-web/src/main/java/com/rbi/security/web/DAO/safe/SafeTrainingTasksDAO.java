package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.LearningInformations;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.entity.web.safe.task.SafeTrainingTasks;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafeTrainingTasksDAO
 * @USER: "吴松达"
 * @DATE: 2020/6/3
 * @TIME: 21:47
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 03
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 21
 * @MINUTE: 47
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeTrainingTasksDAO {
    /**
     * 批量添加任务
     */
    @Insert({
            "<script>",
            "insert into safe_training_tasks (company_personnel_id,training_plan_id,processing_status,idt) values ",
            "<foreach collection='safeTrainingTasksList' item='item' index='index' separator=','>",
            "(#{item.companyPersonnelId},#{item.trainingPlanId},#{item.processingStatus},#{item.idt})",
            "</foreach>",
            "</script>"
    })
    int insertTrainingTasks(@Param("safeTrainingTasksList") List<SafeTrainingTasks> safeTrainingTasksList);

    /**
     * 获取任务下面的未完成试卷信息Processed
     */
    @Select("SELECT tp.id,tp.test_paper_name,stp.processing_status,stp.test_results,tp.start_time,tp.end_time,tp.duration,stp.personnel_training_record_id,tp.exam_notes FROM (SELECT stp.id,stt.processing_status,stt.test_results,stt.personnel_training_record_id FROM " +
            "(SELECT id AS 'personnel_training_record_id',training_plan_id,processing_status,test_results FROM safe_training_tasks WHERE company_personnel_id=#{companyPersonnelId} AND processing_status=#{processingStatus} LIMIT #{startIndex},#{pageSize}) stt\n" +
            "LEFT JOIN safe_training_plan stp ON stt.training_plan_id=stp.id) stp LEFT JOIN safe_test_paper tp ON stp.id=tp.training_plan_id")
    List<TestPaperInfo> pagingUnprocessedTestPaperInfo(@Param("companyPersonnelId")int companyPersonnelId,@Param("processingStatus")int processingStatus,@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 获取自身未完成考试数量
     */
    @Select("SELECT count(id) FROM safe_training_tasks WHERE company_personnel_id=#{companyPersonnelId} AND processing_status=#{processingStatus}")
    int getUnprocessedTestPaperCount(@Param("companyPersonnelId")int companyPersonnelId,@Param("processingStatus")int processingStatus);
    /**
     *获取任务下面已完成的考试信息
     */
    @Select("SELECT tp.id,tp.test_paper_name,stp.processing_status,stp.test_results,tp.start_time,tp.end_time,tp.duration,stp.personnel_training_record_id FROM (SELECT stp.id,stt.processing_status,stt.test_results,stt.personnel_training_record_id FROM" +
            "(SELECT id AS 'personnel_training_record_id',training_plan_id,processing_status,test_results FROM safe_training_tasks WHERE company_personnel_id=#{companyPersonnelId} AND processing_status!=1 LIMIT #{startIndex},#{pageSize}) stt\n" +
            " LEFT JOIN safe_training_plan stp ON stt.training_plan_id=stp.id) stp LEFT JOIN safe_test_paper tp ON stp.id=tp.training_plan_id")
    List<TestPaperInfo> pagingProcessedTestPaperInfo(@Param("companyPersonnelId")int companyPersonnelId,@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 获取已完成考试数量
     */
    @Select("SELECT count(id) FROM safe_training_tasks WHERE company_personnel_id=#{companyPersonnelId} AND processing_status!=1")
    int getProcessedTestPaperCount(@Param("companyPersonnelId")int companyPersonnelId);

    /**
     * 完成考试，更新数据
     */
    @Update("update safe_training_tasks set processing_status=#{processingStatus},test_results=#{testResults} where id=#{id}")
    int updateTrainingTasks(SafeTrainingTasks safeTrainingTasks);
    /**
     * 获取自身学习计划
     */
    @Select("SELECT stp.id,stn.training_content,stn.start_time,stn.end_time,stp.up_to FROM\n" +
            "(SELECT stp.training_needs_id,stp.id,stt.up_to FROM (SELECT stt.training_plan_id,stt.up_to FROM safe_training_tasks stt WHERE stt.company_personnel_id=#{companyPersonnelId} LIMIT #{startIndex},#{pageSize}) stt LEFT JOIN safe_training_plan stp on\n" +
            " stp.id=stt.training_plan_id order by stp.idt asc) stp LEFT JOIN safe_training_needs stn on  stp.training_needs_id=stn.id")
    List<LearningInformations> getLearningInformation(@Param("companyPersonnelId") int companyPersonnelId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 获取自身学习计划数量
     */
    @Select("SELECT count(stt.id) FROM safe_training_tasks stt WHERE stt.company_personnel_id=#{companyPersonnelId}")
    int getLearningInformationCount(@Param("companyPersonnelId") int companyPersonnelId);
    /**
     * 根据id获取计划中的学习内容
     */
    @Select("SELECT stm.* FROM(SELECT sdp.training_materials_id  FROM safe_data_plan sdp WHERE sdp.training_plan_id=#{id}) sdp LEFT JOIN safe_training_materials stm on  stm.id=sdp.training_materials_id")
    List<SafeTrainingMaterials> getTrainingMaterials(@Param("id") int id);

    /**
     * 根据公司人员id和training_plan_id获取人员培训记录id
     */
    @Select("select id from safe_training_tasks where company_personnel_id=#{companyPersonnelId} and training_plan_id=#{trainingPlanId}")
      Integer getId(@Param("companyPersonnelId")int companyPersonnelId,@Param("trainingPlanId")int trainingPlanId);
}
