package com.rbi.security.web.DAO.risk;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.risk.RiskControl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

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
            "workshop_id,workshop_name,class_id,class_name,idt,evaluate_time)values" +
            "(#{riskCode},#{riskType},#{taskCode},#{code},#{organizationId}," +
            "#{organizationName},#{workType},#{step},#{harmName},#{harmKind}," +
            "#{harmDescription},#{riskDescription},#{riskKind},#{riskCategory},#{exposeInfo}," +
            "#{controlMeasures},#{consequence},#{expose},#{possibility},#{riskValue}," +
            "#{riskGrad},#{adviceMeasures},#{measuresEffective},#{measuresCost},#{measuresResult}," +
            "#{measuresUse},#{companyId},#{companyName},#{factoryId},#{factoryName}," +
            "#{workshopId},#{workshopName},#{classId},#{className},#{idt},#{evaluateTime})")
    void addInside(RiskControl riskControl);
}
