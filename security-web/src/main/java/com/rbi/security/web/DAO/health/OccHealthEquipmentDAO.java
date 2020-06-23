package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccHealthEquipment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OccHealthEquipmentDAO {

    /**
     * 获取详情
     */
    @Select("select * from occ_health_equipment where id=#{id}")
    OccHealthEquipment findOccHealthEquipmentById(@Param("id") Integer id);
    /**
     * 分页获取
     */
    @Select("select * from occ_health_equipment limit #{pageNo},#{pageSize}")
    List<OccHealthEquipment> findOccHealthEquipmentByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from occ_health_equipment")
    int findNumOccHealthEquipment();

    /**
     * 添加
     */
    @Insert( "insert into occ_health_equipment (health_equipment_organization,health_equipment_name,health_equipment_model,health_equipment_production," +
            "health_equipment_location,health_equipment_time,health_equipment_remark) value(#{healthEquipmentOrganization}," +
            "#{healthEquipmentName},#{healthEquipmentModel},#{healthEquipmentProduction},#{healthEquipmentLocation},#{healthEquipmentTime}," +
            "#{healthEquipmentRemark})")
    void insertOccHealthEquipment(OccHealthEquipment occHealthEquipment);

    /**
     * 根据id修改
     */
    @Update(value = "update occ_health_equipment set health_equipment_organization=#{healthEquipmentOrganization},health_equipment_name=#{healthEquipmentName},health_equipment_model=#{healthEquipmentModel}," +
            "health_equipment_production=#{healthEquipmentProduction},health_equipment_location=#{healthEquipmentLocation},health_equipment_time=#{healthEquipmentTime}," +
            "health_equipment_remark=#{healthEquipmentRemark} where id=#{id}")
    void updateOccHealthEquipment(OccHealthEquipment occHealthEquipment);

    @Delete("delete from occ_health_equipment where id=#{id}")
    void deleteOccHealthEquipment(@Param("id") Integer id);
}
