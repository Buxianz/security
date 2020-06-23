package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccHealthMaintain;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OccHealthMaintainDAO {

    /**
     * 获取详情
     */
    @Select("select * from occ_health_maintain where id=#{id}")
    OccHealthMaintain findOccHealthMaintainById(@Param("id") Integer id);
    /**
     * 分页获取
     */
    @Select("select * from occ_health_maintain limit #{pageNo},#{pageSize}")
    List<OccHealthMaintain> findOccHealthMaintainByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from occ_health_equipment")
    int findNumOccHealthMaintain();

    /**
     * 添加
     */
    @Insert( "insert into occ_health_maintain (health_maintain_workshop,health_maintain_name,health_maintain_type,health_maintain_time," +
            "health_maintain_location,health_maintain_danger_name,health_maintain_situation,health_maintain_cause,health_maintain_remark) " +
            "value(#{healthMaintainWorkshop},#{healthMaintainName},#{healthMaintainType},#{healthMaintainTime},#{healthMaintainLocation},#{healthMaintainDangerName}," +
            "#{healthMaintainSituation},#{healthMaintainCause},#{healthMaintainRemark})")
    void insertOccHealthMaintain(OccHealthMaintain occHealthMaintain);

    /**
     * 根据id修改
     */
    @Update(value = "update occ_health_maintain set health_maintain_workshop=#{healthMaintainWorkshop},health_maintain_name=#{healthMaintainName},health_maintain_type=#{healthMaintainType}," +
            "health_maintain_time=#{healthMaintainTime},health_maintain_location=#{healthMaintainLocation},health_maintain_danger_name=#{healthMaintainDangerName}," +
            "health_maintain_situation=#{healthMaintainSituation},health_maintain_cause=#{healthMaintainCause},health_maintain_remark=#{healthMaintainRemark} where id=#{id}")
    void updateOccHealthMaintain(OccHealthMaintain occHealthMaintain);

    @Delete("delete from occ_health_maintain where id=#{id}")
    void deleteOccHealthMaintain(@Param("id") Integer id);
}
