package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.SafeTrainingPlan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafeTrainingPlan
 * @USER: "吴松达"
 * @DATE: 2020/6/3
 * @TIME: 10:52
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 03
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 52
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeTrainingPlanDAO {
    @Insert("insert into safe_training_plan (training_needs_id,target_set,idt) values (#{trainingNeedsId},#{targetSet},#{idt})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")//此语句代表添加后返回主键
    int addTrainingPlan(SafeTrainingPlan safeTrainingPlan);
}
