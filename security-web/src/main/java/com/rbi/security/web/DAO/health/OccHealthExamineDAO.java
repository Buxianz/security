package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccDiseaseFactors;
import com.rbi.security.entity.web.health.OccHealthExamine;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.health
 * @NAME: OccHealthExamineDAO
 * @USER: "谢青"
 * @DATE: 2020/6/24
 * @TIME: 10:34
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 24
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 34
 * @PROJECT_NAME: security
 **/
@Mapper
public interface OccHealthExamineDAO {

    @Select("select * from occ_health_examine limit #{pageNo},#{pageSize}")
    List<OccHealthExamine> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from occ_health_examine")
    int findByPageNum();

    @Select("select count(*) from occ_health_examine where id_num = #{idNum}")
    int findNum(String idNum);

    @Select("select count(*) from occ_health_examine where id_num = #{idNum} and id != #{id}")
    int findNumExceptId(String idNum, Integer id);

    @Insert("insert into occ_health_examine (name,gender,age,marriage,phone," +
            "organization,id_num,factor,work_type,deadline," +
            "work_time,leave_time,remark,unit_name,reserve_time," +
            "idt,operating_staff) values (#{name},#{gender},#{age},#{marriage},#{phone}," +
            "#{organization},#{idNum},#{factor},#{workType},#{deadline}," +
            "#{workTime},#{leaveTime},#{remark},#{unitName},#{reserveTime}," +
            "#{idt},#{operatingStaff})")
    void add(OccHealthExamine occHealthExamine);

    @Update("update occ_health_examine set name=#{name},gender=#{gender},age=#{age},marriage=#{marriage},phone=#{phone}," +
            "organization=#{organization},id_num=#{idNum},factor=#{factor},work_type=#{workType},deadline=#{deadline}," +
            "work_time=#{workTime},leave_time=#{leaveTime},remark=#{remark},unit_name=#{unitName},reserve_time=#{reserveTime}," +
            "udt=#{udt} where id = #{id}")
    void update(OccHealthExamine occHealthExamine);

    @Delete("delete from occ_health_examine where id =#{id}")
    void delete(int id);
}
