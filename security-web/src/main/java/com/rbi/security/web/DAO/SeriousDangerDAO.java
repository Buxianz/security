package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.SeriousDanger.PagingSeriousDanger;
import com.rbi.security.entity.web.entity.SeriousDanger;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeriousDangerDAO {

    /**
     * 根据serious_danger_name分页获取
     */
    @Select("select * from serious_danger where serious_danger_name like ${seriousDangerName} order by id DESC limit #{pageNo},#{pageSize}")
    List<PagingSeriousDanger> findSeriousDangerByPageAndName(@Param("seriousDangerName") String seriousDangerName,
                                                       @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from serious_danger where serious_danger_name like ${seriousDangerName}")
    int findNumSeriousDangerByName(@Param("seriousDangerName") String seriousDangerName);

    /**
     * 根据serious_danger_name获取
     */
    @Select("select * from serious_danger where serious_danger_name=#{seriousDangerName}")
    PagingSeriousDanger findSeriousDangerByName(@Param("seriousDangerName") String seriousDangerName);

    /**
     * 根据serious_danger_name分页获取
     */
    @Select("select * from serious_danger where id=#{id}")
    PagingSeriousDanger findSeriousDangerByID(@Param("id") Integer id);
    /**
     * 分页获取
     */
    @Select("select * from serious_danger order by id DESC limit #{pageNo},#{pageSize}")
    List<PagingSeriousDanger> findSeriousDangerByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from serious_danger")
    int findNumSeriousDanger();

    /**
     * 添加
     */
    @Insert( "insert into serious_danger (serious_danger_name,serious_danger_location,serious_danger_element,serious_danger_measure," +
            "serious_danger_status,serious_danger_cycle,serious_danger_principal,serious_danger_time,serious_danger_control_level,serious_danger_emergency_measure) value(#{seriousDangerName}," +
            "#{seriousDangerLocation},#{seriousDangerElement},#{seriousDangerMeasure},#{seriousDangerStatus},#{seriousDangerCycle}," +
            "#{seriousDangerPrincipal},#{seriousDangerTime},#{seriousDangerControlLevel},#{seriousDangerEmergencyMeasure})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    void insertSeriousDanger(SeriousDanger seriousDanger);

    /**
     * 根据id修改
     */
    @Update(value = "update serious_danger set serious_danger_name=#{seriousDangerName},serious_danger_location=#{seriousDangerLocation},serious_danger_element=#{seriousDangerElement}," +
            "serious_danger_measure=#{seriousDangerMeasure},serious_danger_status=#{seriousDangerStatus},serious_danger_cycle=#{seriousDangerCycle}," +
            "serious_danger_principal=#{seriousDangerPrincipal},serious_danger_time=#{seriousDangerTime},serious_danger_control_level=#{seriousDangerControlLevel},serious_danger_emergency_measure=#{seriousDangerEmergencyMeasure} where id=#{id}")
    void updateSeriousDanger(SeriousDanger seriousDanger);

}
