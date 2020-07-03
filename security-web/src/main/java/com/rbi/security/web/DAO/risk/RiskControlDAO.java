package com.rbi.security.web.DAO.risk;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.risk.RiskControl;
import com.rbi.security.entity.web.risk.RiskControlPicture;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.risk
 * @NAME: RiskControlDAO
 * @USER: "谢青"
 * @DATE: 2020/6/18
 * @TIME: 14:43
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 14
 * @MINUTE: 43
 * @PROJECT_NAME: security
 **/
@Mapper
public interface RiskControlDAO {
    @Select("select * from sys_company_personnel where sys_company_personnel.id = #{id}")
    SysCompanyPersonnel findPersonnelById(@Param("id") Integer id);

    @Insert("insert into risk_control_picture (risk_code,picture)values(#{riskCode},#{picture})")
    void addPicture(String riskCode, String picture);

    @Select("select * from sys_organization where id = #{id}")
    SysOrganization findAllByOrganizationId(Integer organizationId);

    @Insert("insert into risk_control(risk_code,risk_type,task_code,code,organization_id," +
            "organization_name,work_type,step,harm_name,harm_kind," +
            "harm_description,risk_description,risk_kind,risk_category,expose_info," +
            "control_measures,consequence,expose,possibility,risk_value," +
            "risk_grad,advice_measures,measures_effective,measures_cost,measures_result," +
            "measures_use,company_id,company_name,factory_id,factory_name," +
            "workshop_id,workshop_name,class_id,class_name,idt,evaluate_time,area,operating_staff)values" +
            "(#{riskCode},#{riskType},#{taskCode},#{code},#{organizationId}," +
            "#{organizationName},#{workType},#{step},#{harmName},#{harmKind}," +
            "#{harmDescription},#{riskDescription},#{riskKind},#{riskCategory},#{exposeInfo}," +
            "#{controlMeasures},#{consequence},#{expose},#{possibility},#{riskValue}," +
            "#{riskGrad},#{adviceMeasures},#{measuresEffective},#{measuresCost},#{measuresResult}," +
            "#{measuresUse},#{companyId},#{companyName},#{factoryId},#{factoryName}," +
            "#{workshopId},#{workshopName},#{classId},#{className},#{idt},#{evaluateTime},#{area},#{operatingStaff})")
    void add(RiskControl riskControl);

    @Select("select count(*) from risk_control_picture where risk_code = #{riskCode}")
    int findPictureNum(@Param("riskCode") String riskCode);


    @Update("update risk_control set task_code = #{taskCode},code = #{code},organization_id = #{organizationId}," +
            "organization_name=#{organizationName},work_type=#{workType},area=#{area},step=#{step},harm_name=#{harmName},harm_kind=#{harmKind}," +
            "harm_description=#{harmDescription},risk_description=#{riskDescription},risk_kind=#{riskKind},risk_category=#{riskCategory},expose_info=#{exposeInfo}," +
            "control_measures=#{controlMeasures},consequence=#{consequence},expose=#{expose},possibility=#{possibility},risk_value=#{riskValue}," +
            "risk_grad=#{riskGrad},advice_measures=#{adviceMeasures},measures_effective=#{measuresEffective},measures_cost=#{measuresCost},measures_result=#{measuresResult}," +
            "measures_use=#{measuresUse},company_id=#{companyId},company_name=#{companyName},factory_id=#{factoryId},factory_name=#{factoryName}," +
            "workshop_id=#{workshopId},workshop_name=#{workshopName},class_id=#{classId},class_name=#{className},udt=#{udt},evaluate_time=#{evaluateTime} where risk_code = #{riskCode}")
    void update(RiskControl riskControl);



    @Select("select * from risk_control_picture where risk_code = #{riskCode}")
    List<RiskControlPicture> findPictureByRiskCode(@Param("riskCode") String riskCode);

    @Delete("delete from risk_control_picture where id = #{id}")
    void deleteByPictureId(int id);

    @Select("select * from risk_control where risk_type = #{riskType} order by id DESC limit #{pageNo},#{pageSize}")
    List<RiskControl> findByPage(@Param("riskType") String riskType,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from risk_control where risk_type = #{riskType}")
    int findNum(@Param("riskType") String riskType);

    @Select("select * from risk_control where risk_grad = #{riskGrad} order by id DESC limit #{pageNo},#{pageSize}")
    List<RiskControl> findSeriousRiskByPage(@Param("riskGrad") String riskGrad,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from risk_control where risk_grad = #{riskGrad}")
    int findSeriousRiskByPageNum(@Param("riskGrad") String riskGrad);


    @Select("select * from risk_control where risk_type = #{riskType} and organization_name like ${organizationName} order by id DESC limit #{pageNo},#{pageSize}")
    List<RiskControl> findUnitByPage(@Param("riskType") String riskType,@Param("organizationName") String organizationName,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from risk_control where risk_type = #{riskType} and organization_name like ${organizationName}")
    int findUnitByPageNum(@Param("riskType") String riskType,@Param("organizationName") String organizationName);


    @Select("select * from risk_control where risk_type = #{riskType} and work_type like ${workType} order by id DESC limit #{pageNo},#{pageSize}")
    List<RiskControl> findWorkTypeByPage(@Param("riskType") String riskType,@Param("workType") String workType,
                                         @Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from risk_control where risk_type = #{riskType} and work_type like ${workType}")
    int findWorkTypeByPageNum(@Param("riskType") String riskType,@Param("workType") String workType);




    @Select("select * from risk_control where risk_grad = '一级' and organization_name like ${organizationName} order by id DESC limit #{pageNo},#{pageSize}")
    List<RiskControl> findSeriousUnitByPage(@Param("organizationName") String organizationName,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from risk_control where risk_grad = '一级' and organization_name like ${organizationName}")
    int findSeriousUnitByPageNum(@Param("organizationName") String organizationName);

    @Select("select * from risk_control where risk_grad = '一级' and work_type like ${workType} order by id DESC limit #{pageNo},#{pageSize}")
    List<RiskControl> findSeriousWorkTypeByPage(@Param("workType") String workType,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from risk_control where risk_grad = '一级' and work_type like ${workType}")
    int findSeriousWorkTypeByPageNum(@Param("workType") String workType);
}
