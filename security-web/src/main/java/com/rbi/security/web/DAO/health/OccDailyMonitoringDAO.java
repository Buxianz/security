package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.health
 * @NAME: OccDailyMonitoringDAO
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 11:20
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 11
 * @MINUTE: 20
 * @PROJECT_NAME: security
 **/
@Mapper
public interface OccDailyMonitoringDAO {

    @Select("select * from occ_daily_monitoring order by id DESC limit #{pageNo},#{pageSize}")
    List<OccDailyMonitoring> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from occ_daily_monitoring")
    int findByPageNum();

    @Delete("delete from occ_daily_monitoring where id = #{id}")
    void delete(int id);

    @Insert("insert into occ_daily_monitoring (workshop_name,determination_date,factory_name,harmful_factors,determination_place," +
            "determination_result,determination_unit,remark,writer,auditor," +
            "principal,idt,operating_staff) values (#{workshopName},#{determinationDate},#{factoryName},#{harmfulFactors},#{determinationPlace}," +
            "#{determinationResult},#{determinationUnit},#{remark},#{writer},#{auditor}," +
            "#{principal},#{idt},#{operatingStaff})")
    void add(OccDailyMonitoring occDailyMonitoring);

    @Update("update occ_daily_monitoring set " +
            "workshop_name=#{workshopName},determination_date=#{determinationDate},factory_name=#{factoryName}," +
            "harmful_factors=#{harmfulFactors},determination_place=#{determinationPlace},determination_result=#{determinationResult}," +
            "determination_unit=#{determinationUnit},remark=#{remark},writer=#{writer}," +
            "auditor=#{auditor},principal=#{principal},udt=#{udt} where id = #{id}")
    void update(OccDailyMonitoring occDailyMonitoring);
}
