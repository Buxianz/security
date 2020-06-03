package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: TrainingContentServiceDAO
 * @USER: "谢青"
 * @DATE: 2020/6/2
 * @TIME: 10:52
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 02
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 52
 * @PROJECT_NAME: security
 **/
@Mapper
public interface TrainingContentServiceDAO {
    @Insert("insert into safe_data_plan (training_plan_id,training_materials_id) values (#{trainingPlanId},#{trainingMaterialsId})")
    void add(SafeDataPlan safeDataPlan);

    @Select("select count(*) from safe_data_plan where training_plan_id=#{trainingPlanId} and training_materials_id = #{trainingMaterialsId}")
    Integer findCount(@Param("trainingPlanId")Integer trainingPlanId,@Param("trainingMaterialsId")Integer trainingMaterialsId);

    @Select("select safe_data_plan.id,safe_training_materials.resource_name " +
            "from safe_data_plan,safe_training_materials " +
            "where safe_data_plan.training_materials_id= safe_training_materials.id and " +
            "safe_data_plan.training_plan_id = #{trainingPlanId}")
    List<SafeDataPlanDTO> findAllByTrainingPlanId(Integer trainingPlanId);

    @Delete("delete from safe_data_plan where id = #{id}")
    void deleteById(Integer id);

    @Select("select p.id,m.resource_name,m.resource_path from safe_data_plan as p,safe_training_materials as m " +
            "where p.training_materials_id= m.id and p.training_plan_id = #{trainingPlanId} and m.resource_type != 'mp4'")
    List<SafeDataPlanDTO> findDataById(Integer trainingPlanId);

    @Select("select p.id,m.resource_name,m.resource_path from safe_data_plan as p,safe_training_materials as m " +
            "where p.training_materials_id= m.id and p.training_plan_id = #{trainingPlanId} and m.resource_type = 'mp4'")
    List<SafeDataPlanDTO> findVideoById(Integer trainingPlanId);
}
