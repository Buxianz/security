package com.rbi.security.web.DAO;

import com.rbi.security.entity.AuthenticationUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface LoginVerificationDAO {
    /**
     * 获取用户的基本信息
     * @param
     * @return
     */
    @Select("SELECT username,`password`,company_personnel_id,enabled,salf FROM sys_user WHERE username=#{username}")
    AuthenticationUserDTO getUserByUserName(@Param("username") String username);
}
