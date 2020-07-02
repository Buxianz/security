package com.rbi.security.web.service;

import com.rbi.security.entity.AuthenticationUserDTO;

import java.util.Set;

public interface LoginVerificationService {
    AuthenticationUserDTO getUserPrincipalInfo(String userName) throws RuntimeException;
    Set<String> getUserRoleName(int userCode) throws RuntimeException;
    Set<String> getUserPermissionOperateCode(String username, int systemId) throws RuntimeException;
}
