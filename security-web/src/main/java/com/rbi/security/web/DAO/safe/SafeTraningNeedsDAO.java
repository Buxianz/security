package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.demand.PagingTraniningNeeds;
import com.rbi.security.entity.web.safe.demand.SafeTrainingNeeds;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafeTraningNeedsDAO
 * @USER: "吴松达"
 * @DATE: 2020/6/1
 * @TIME: 14:40
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 14
 * @MINUTE: 40
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeTraningNeedsDAO {
    /**
     * 添加需求计划
     * @param safeTrainingNeeds
     * @return
     */
    @Insert("insert into safe_training_needs (target_set,training_type_id,training_content,training_duration,start_time,end_time,organization_training_department_id," +
            "proposed_time,report_person,processing_status,idt) " +
            "values (#{targetSet},#{trainingTypeId},#{trainingContent},#{trainingDuration},#{startTime},#{endTime},#{organizationTrainingDepartmentId},#{proposedTime},#{reportPerson},#{processingStatus},#{idt})")
    int insertSafeTraningNeeds(SafeTrainingNeeds safeTrainingNeeds);
    /**
     * 获取未处理记录数量
     */
    @Select("select count(id) from safe_training_needs  WHERE  processing_status=1")
    int getUnprocessedTrainingNeeds();
    /**
     * 获取已处理记录数量
     */
    @Select("select count(id) from safe_training_needs  WHERE  processing_status=1")
    int getProcessedTrainingNeeds();
    /**
     * 分页查看未处理需求记录Processed
     */
    @Select("SELECT stn.*,scp.`name` FROM \n" +
            "(select stn.id,sty.training_type_name,stn.training_content,stn.processing_status,stn.report_person,proposed_time from safe_training_needs stn \n" +
            "LEFT JOIN safa_training_type  sty ON stn.training_type_id=sty.id WHERE  processing_status=#{processingStatus} ORDER BY proposed_time LIMIT 0,10) stn LEFT JOIN sys_company_personnel scp ON scp.id=stn.report_person")
    List<PagingTraniningNeeds> pagingUnprocessedSafeTraningNeeds(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("processingStatus") int processingStatus);
    @Select("SELECT stn.*,scp.`name` FROM \n" +
            "(select stn.id,sty.training_type_name,stn.training_content,stn.processing_status,stn.report_person,proposed_time from safe_training_needs stn \n" +
            "LEFT JOIN safa_training_type  sty ON stn.training_type_id=sty.id WHERE  processing_status=1 ORDER BY proposed_time LIMIT 0,10) stn LEFT JOIN sys_company_personnel scp ON scp.id=stn.report_person")
    List<PagingTraniningNeeds> pagingProcessedSafeTraningNeeds(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
