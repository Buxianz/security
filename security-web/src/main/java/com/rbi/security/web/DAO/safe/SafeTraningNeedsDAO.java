package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.demand.PagingTraniningNeeds;
import com.rbi.security.entity.web.safe.demand.SafeTrainingNeeds;
import org.apache.ibatis.annotations.*;
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
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeTraningNeeds(SafeTrainingNeeds safeTrainingNeeds);
    /**
     * 获取未处理记录数量
     */
    @Select("select count(id) from safe_training_needs  WHERE  processing_status=1")
    int getUnprocessedTrainingNeeds();
    /**
     * 获取已处理记录数量
     */
    @Select("select count(id) from safe_training_needs  WHERE  processing_status!=1")
    int getProcessedTrainingNeeds();
    /**
     * 分页查看未处理需求记录Processed
     */
    @Select("SELECT stn.*,scp.`name` FROM \n" +
            "(select stn.id,sty.training_type_name,stn.training_content,stn.processing_status,stn.report_person,proposed_time from safe_training_needs stn \n" +
            "LEFT JOIN safa_training_type  sty ON stn.training_type_id=sty.id WHERE  processing_status=#{processingStatus} ORDER BY proposed_time DESC LIMIT #{startIndex},#{pageSize}) stn LEFT JOIN sys_company_personnel scp ON scp.id=stn.report_person")
    List<PagingTraniningNeeds> pagingUnprocessedSafeTraningNeeds(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("processingStatus") int processingStatus);
    @Select("SELECT stn.*,scp.`name` FROM \n" +
            "(select stn.id,sty.training_type_name,stn.training_content,stn.processing_status,stn.report_person,proposed_time from safe_training_needs stn \n" +
            "LEFT JOIN safa_training_type  sty ON stn.training_type_id=sty.id WHERE  processing_status!=1 ORDER BY proposed_time DESC  LIMIT #{startIndex},#{pageSize}) stn LEFT JOIN sys_company_personnel scp ON scp.id=stn.report_person")
    List<PagingTraniningNeeds> pagingProcessedSafeTraningNeeds(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 更新需求提报
     */
    @Update("update safe_training_needs set target_set=#{targetSet},operation_reason=#{operationReason},processing_time=#{processingTime},processing_status=#{processingStatus},operating_staff=#{operatingStaff} where id=#{id}")
    int updateTrainingNeeds(SafeTrainingNeeds safeTrainingNeeds);

    /**
     * 判断数据是否已经处理
     */
    @Select("select * from safe_training_needs where id=#{id} and processing_status=1")
    SafeTrainingNeeds getTrainingNeedsByIdAndStatus(SafeTrainingNeeds safeTrainingNeeds);
    /**
     * 获取数据
     */
    @Select("SELECT stn.*,sty.training_type_name FROM\n" +
            "(SELECT stn.*,so.organization_name FROM\n" +
            "(select * from safe_training_needs where id=#{id}) stn LEFT JOIN sys_organization so on stn.organization_training_department_id=so.id) stn left JOIN safa_training_type sty on stn.training_type_id=sty.id")
    SafeTrainingNeeds getTrainingNeedsById(@Param("id") int id);
    /**
     * 获取需求计划开始与结束时间
     */
    @Select("SELECT CONCAT(start_time,\"至\",end_time) FROM safe_training_needs WHERE id=#{id}")
    String getStartAndEndTime(@Param("id")int id);

    /**
     * 分页获取请求人所在部门及其子部门下的未处理提报需求
     */
    @Select("SELECT stn.id,sty.training_type_name,stn.training_content,stn.processing_status,stn.report_person,stn.`name`,stn.proposed_time FROM\n" +
            "(SELECT stn.*,scp.`name` FROM\n" +
            "(SELECT scp.id as 'company_personnel_id',scp.`name` FROM \n" +
            "(select id as 'organization_id'  from (\n" +
            "                          select t1.id,\n" +
            "                          if(find_in_set(parent_id, @pids) > 0 OR find_in_set(id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "                        from (\n" +
            "                               select id,parent_id from sys_organization \n" +
            "                             ) t1,\n" +
            "                              (select @pids := #{organizationId}) t2\n" +
            "            ) t3 where ischild != 0) t4 INNER JOIN sys_company_personnel scp on scp.organization_id=t4.organization_id) scp INNER JOIN " +
            "safe_training_needs stn on stn.report_person=scp.company_personnel_id WHERE  processing_status=#{processingStatus} ORDER BY proposed_time DESC  LIMIT #{startIndex},#{pageSize})stn LEFT JOIN safa_training_type sty ON stn.training_type_id=sty.id")
    List<PagingTraniningNeeds> getUnprocessedOrganizationNeedsByOrganizationId(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("organizationId") int organizationId,@Param("processingStatus") int processingStatus);
    /**
     * 获取未处理记录数
     */
    @Select("SELECT count(stn.id) FROM\n" +
            "(SELECT scp.id as 'company_personnel_id',scp.`name` FROM \n" +
            "(select id as 'organization_id'  from (\n" +
            "                          select t1.id,\n" +
            "                          if(find_in_set(parent_id, @pids) > 0 OR find_in_set(id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "                        from (\n" +
            "                               select id,parent_id from sys_organization \n" +
            "                             ) t1,\n" +
            "                              (select @pids := #{organizationId}) t2\n" +
            "            ) t3 where ischild != 0) t4 INNER JOIN sys_company_personnel scp on scp.organization_id=t4.organization_id) scp INNER JOIN safe_training_needs " +
            "stn on stn.report_person=scp.company_personnel_id where processing_status=#{processingStatus}")
   int  getUnprocessedOrganizationNeedsConutByOrganizationId(@Param("organizationId") int organizationId,@Param("processingStatus") int processingStatus);
    /**
     * 分页获取请求人所在部门及其子部门下的未处理提报需求
     */
    @Select("SELECT stn.id,sty.training_type_name,stn.training_content,stn.processing_status,stn.report_person,stn.`name`,stn.proposed_time FROM\n" +
            "(SELECT stn.*,scp.`name` FROM\n" +
            "(SELECT scp.id as 'company_personnel_id',scp.`name` FROM \n" +
            "(select id as 'organization_id'  from (\n" +
            "                          select t1.id,\n" +
            "                          if(find_in_set(parent_id, @pids) > 0 OR find_in_set(id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "                        from (\n" +
            "                               select id,parent_id from sys_organization \n" +
            "                             ) t1,\n" +
            "                              (select @pids := #{organizationId}) t2\n" +
            "            ) t3 where ischild != 0) t4 INNER JOIN sys_company_personnel scp on scp.organization_id=t4.organization_id) scp INNER JOIN " +
            "safe_training_needs stn on stn.report_person=scp.company_personnel_id WHERE  processing_status!=1 ORDER BY proposed_time DESC  LIMIT #{startIndex},#{pageSize})stn LEFT JOIN safa_training_type sty ON stn.training_type_id=sty.id")
    List<PagingTraniningNeeds> getProcessedOrganizationNeedsByOrganizationId(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("organizationId") int organizationId);
    /**
     * 获取处理记录数
     */
    @Select("SELECT count(stn.id) FROM\n" +
            "(SELECT scp.id as 'company_personnel_id',scp.`name` FROM \n" +
            "(select id as 'organization_id'  from (\n" +
            "                          select t1.id,\n" +
            "                          if(find_in_set(parent_id, @pids) > 0 OR find_in_set(id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "                        from (\n" +
            "                               select id,parent_id from sys_organization \n" +
            "                             ) t1,\n" +
            "                              (select @pids := #{organizationId}) t2\n" +
            "            ) t3 where ischild != 0) t4 INNER JOIN sys_company_personnel scp on scp.organization_id=t4.organization_id) scp INNER JOIN safe_training_needs " +
            "stn on stn.report_person=scp.company_personnel_id where processing_status!=1")
    int  getProcessedOrganizationNeedsConutByOrganizationId(@Param("organizationId") int organizationId);

}
