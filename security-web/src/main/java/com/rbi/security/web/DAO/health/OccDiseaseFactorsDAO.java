package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccDiseaseFactors;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.health
 * @NAME: OccDiseaseFactorsDAO
 * @USER: "谢青"
 * @DATE: 2020/6/23
 * @TIME: 14:45
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 14
 * @MINUTE: 45
 * @PROJECT_NAME: security
 **/
@Mapper
public interface OccDiseaseFactorsDAO {

    @Select("select * from occ_disease_factors limit #{pageNo},#{pageSize}")
    List<OccDiseaseFactors> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from occ_disease_factors")
    int findByPageNum();

    @Insert("insert into occ_disease_factors " +
            "(factor,total_num,women_num,training_num,medical_examination_num," +
            "occ_disease_num,demobilized_num,writer,write_department,write_time," +
            "training_organization,training_time,medical_examination_organization,medical_examination_time,idt," +
            "operating_staff) " +
            "values " +
            "(#{factor},#{totalNum},#{womenNum},#{trainingNum},#{medicalExaminationNum}," +
            "#{occDiseaseNum},#{demobilizedNum},#{writer},#{writeDepartment},#{writeTime}," +
            "#{trainingOrganization},#{trainingTime},#{medicalExaminationOrganization},#{medicalExaminationTime},#{idt}," +
            "#{operatingStaff})")
    void add(OccDiseaseFactors occDiseaseFactors);

    @Update("update occ_disease_factors set factor=#{factor},total_num=#{totalNum},women_num=#{womenNum},training_num=#{trainingNum},medical_examination_num=#{medicalExaminationNum}," +
            "occ_disease_num=#{occDiseaseNum},demobilized_num=#{demobilizedNum},writer=#{writer},write_department=#{writeDepartment},write_time=#{writeTime}," +
            "training_organization=#{trainingOrganization},training_time=#{trainingTime},medical_examination_organization=#{medicalExaminationOrganization},medical_examination_time=#{medicalExaminationTime},udt=#{udt} where " +
            "id = #{id}")
    void update(OccDiseaseFactors occDiseaseFactors);

    @Delete("delete from occ_disease_factors where id = #{id}")
    void delete(int id);

    @Select("select count(*) from occ_disease_factors where factor = #{factor}")
    int findNum(String factor);

    @Select("select count(*) from occ_disease_factors where factor = #{factor} and id != #{id}")
    int findNumExceptId(String factor, Integer id);
}
