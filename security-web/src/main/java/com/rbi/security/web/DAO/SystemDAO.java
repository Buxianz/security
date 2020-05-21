package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SystemDAO {
    @Select("select id,system_name from sys_system")
    List<SysSystem> getAllSystem();


    /**林新元******/
    /**
     * 查询系统名称
     * @param id
     * @return
     */
    @Select("select * from sys_system WHERE id = #{id}" )
    SysSystem findSystemById(@Param("id") Integer id);
}
