package com.rbi.security.web.DAO;

import com.rbi.security.entity.AuthenticationUserDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LoginVerificationDAO {
    /**
     * 获取用户的基本信息
     * @param userName
     * @return
     */
    @Select("SELECT user_name,`password`,salt,enabled FROM sys_user WHERE user_name=#{userName}")
    AuthenticationUserDTO getUserByUserName(@Param("userName") String userName);
}
