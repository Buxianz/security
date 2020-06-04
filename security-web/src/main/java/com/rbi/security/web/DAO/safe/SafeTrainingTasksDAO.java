package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.task.SafeTrainingTasks;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
