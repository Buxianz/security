package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SystemDAO {
    @Select("select id,system_name from sys_system")
    List<SysSystem> getAllSystem();
}
