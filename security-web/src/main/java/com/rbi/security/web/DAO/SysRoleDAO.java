package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface SysRoleDAO {
    @Select("select * from sys_role")
    List<SysRole> getALlRole();
}
