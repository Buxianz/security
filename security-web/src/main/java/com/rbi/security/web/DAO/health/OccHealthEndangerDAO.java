package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccHealthEndanger;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OccHealthEndangerDAO {
    /**
     * 获取详情
     */
    @Select("select * from occ_health_endanger where id=#{id}")
    OccHealthEndanger findOccHealthEndangerById(@Param("id") Integer id);
    /**
     * 分页获取
     */
    @Select("select * from occ_health_endanger limit #{pageNo},#{pageSize}")
    List<OccHealthEndanger> findOccHealthEndangerByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from occ_health_equipment")
    int findNumOccHealthEndanger();

    /**
     * 添加
     */
    @Insert( "insert into occ_health_endanger (health_endanger_name,health_endanger_position,health_endanger_source,health_endanger_status," +
            "health_endanger_mode,health_endanger_insulate,health_endanger_people_number,health_endanger_female_number,health_endanger_strength,health_endanger_equipment," +
            "health_endanger_goods,health_endanger_department,health_endanger_operation,health_endanger_auditor,health_endanger_operation_time,health_endanger_test,health_endanger_test_time) " +
            "value(#{healthEndangerName},#{healthEndangerPosition},#{healthEndangerSource},#{healthEndangerStatus},#{healthEndangerMode},#{healthEndangerInsulate}," +
            "#{healthEndangerPeopleNumber},#{healthEndangerFemaleNumber},#{healthEndangerStrength},#{healthEndangerEquipment},#{healthEndangerGoods},#{healthEndangerDepartment}," +
            "#{healthEndangerOperation},#{healthEndangerAuditor},#{healthEndangerOperationTime},#{healthEndangerTest},#{healthEndangerTestTime})")
    void insertOccHealthEndanger(OccHealthEndanger occHealthEndanger);

    /**
     * 根据id修改
     */
    @Update(value = "update occ_health_endanger set health_endanger_name=#{healthEndangerName},health_endanger_position=#{healthEndangerPosition},health_endanger_source=#{healthEndangerSource}," +
            "health_endanger_status=#{healthEndangerStatus},health_endanger_mode=#{healthEndangerMode},health_endanger_insulate=#{healthEndangerInsulate}," +
            "health_endanger_people_number=#{healthEndangerPeopleNumber},health_endanger_female_number=#{healthEndangerFemaleNumber},health_endanger_strength=#{healthEndangerStrength},health_endanger_equipment=#{healthEndangerEquipment}," +
            "health_endanger_goods=#{healthEndangerGoods},health_endanger_department=#{healthEndangerDepartment},health_endanger_operation=#{healthEndangerOperation},health_endanger_auditor=#{healthEndangerAuditor}," +
            "health_endanger_operation_time=#{healthEndangerOperationTime},health_endanger_test=#{healthEndangerTest},health_endanger_test_time=#{healthEndangerTestTime} where id=#{id}")
    void updateOccHealthEndanger(OccHealthEndanger occHealthEndanger);

    @Delete("delete from occ_health_endanger where id=#{id}")
    void deleteOccHealthEndanger(@Param("id") Integer id);


    /**
     * 根据职业病危害名称（代码）获取
     */
    @Select("select * from occ_health_endanger where health_endanger_name=#{healthEndangerName}")
    OccHealthEndanger findOccHealthEndangerByHealthEndangerName(@Param("healthEndangerName") String healthEndangerName);
}
