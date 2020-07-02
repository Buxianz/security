package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.web.DAO.LoginVerificationDAO;
import com.rbi.security.web.service.LoginVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class LoginVerificationServiceImp implements LoginVerificationService {
    private static final Logger logger = LoggerFactory.getLogger(LoginVerificationServiceImp.class);
    @Autowired
    LoginVerificationDAO loginVerificationDAO;
    @Override
    public AuthenticationUserDTO getUserPrincipalInfo(String username) throws RuntimeException {
        AuthenticationUserDTO authenticationUserDTO=null;
        try{
            authenticationUserDTO=loginVerificationDAO.getUserByUserName(username);

        }catch (Exception e){
            logger.error("系统异常，异常信息为{}",e);
            throw new RuntimeException();
        }
        return authenticationUserDTO;
    }

    @Override
    public Set<String> getUserRoleName(int userCode) throws RuntimeException {
        return null;
    }


    public Set<String> getUserPermissionOperateCode(String username, int systemId) throws RuntimeException {
        try{
           return loginVerificationDAO.getUserPermissionOperateCode(username,systemId);
        }catch (Exception e){
            logger.error("获取用户权限操作码失败，异常信息为{}",e);
            throw new RuntimeException("获取用户权限操作码失败");
        }
    }
}
