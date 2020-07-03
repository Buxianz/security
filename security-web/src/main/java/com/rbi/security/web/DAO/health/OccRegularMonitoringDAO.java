package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccRegularMonitoring;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.health
 * @NAME: OccRegularMonitoringDAO
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 15:35
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 15
 * @MINUTE: 35
 * @PROJECT_NAME: security
 **/
@Mapper
public interface OccRegularMonitoringDAO {

    @Select("select * from occ_regular_monitoring order by id DESC limit #{pageNo},#{pageSize}")
    List<OccRegularMonitoring> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from occ_regular_monitoring")
    int findByPageNum();

    @Insert("insert into occ_regular_monitoring (time,monitoring_organization,monitoring_project,monitoring_result,annex,idt,operating_staff) values " +
            "(#{time},#{monitoringOrganization},#{monitoringProject},#{monitoringResult},#{annex},#{idt},#{operatingStaff})")
    void add(OccRegularMonitoring occRegularMonitoring);

    @Update("update occ_regular_monitoring set time=#{time},monitoring_organization=#{monitoringOrganization},monitoring_project=#{monitoringProject}," +
            "monitoring_result=#{monitoringResult},annex = #{annex},udt=#{udt} where id = #{id}")
    void update(OccRegularMonitoring occRegularMonitoring);

    @Delete("delete from occ_regular_monitoring where id = #{id}")
    void delete(int id);

    @Update("update occ_regular_monitoring set annex = '' where id =#{id}")
    void updateAnnex(Integer id);

    @Select("select annex from occ_regular_monitoring where id = #{id}")
    String findAnnex(Integer id);
}
