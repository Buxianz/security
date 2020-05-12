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
    @Select("SELECT user_code,user_name,`password`,salt,enabled FROM sys_user WHERE user_name=#{userName}")
    AuthenticationUserDTO getUserByUserName(@Param("userName") String userName);

    /**
     * @param userCode
     * @return
     */
    @Select("SELECT sp.operate_code from (SELECT DISTINCT(sr.permission_id) FROM\n" +
            "            (SELECT sur.company_dept_role_id FROM sys_user_role sur WHERE sur.user_code=#{userCode}) sur LEFT JOIN \n" +
            "            sys_company_dept_role_permission sr ON sur.company_dept_role_id=sr.company_dept_role_id WHERE sr.permission_id is NOT null) sr LEFT JOIN sys_permission sp\n" +
            "ON sr.permission_id=sp.permission_id WHERE sp.system_id=(SELECT system_id from sys_system where system_key=#{systemKey})")
    List<String> getUserPermissionByUserCode(@Param("userCode") int userCode,@Param("systemKey") String systemKey);
}
