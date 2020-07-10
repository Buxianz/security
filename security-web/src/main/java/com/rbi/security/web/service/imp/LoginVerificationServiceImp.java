package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.config.PermissionTreeInfo;
import com.rbi.security.web.DAO.LoginVerificationDAO;
import com.rbi.security.web.service.LoginVerificationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    /**
     *
     */
    public List<PermissionTreeInfo> getUserPermissionTree() throws RuntimeException {
        try{
            Subject subject = SecurityUtils.getSubject();
            AuthenticationUserDTO user=(AuthenticationUserDTO)subject.getPrincipal();
            List<PermissionTreeInfo> permissionTreeInfoList=loginVerificationDAO.getUserPermissionByUserId(user.getId());

            return listToPermissionTree(permissionTreeInfoList);//loginVerificationDAO.getUserPermissionOperateCode(username,systemId);
        }catch (Exception e){
            logger.error("获取用户权限菜单树失败，异常信息为{}",e);
            throw new RuntimeException("获取用户权限菜单树失败");
        }
    }
    private   List<PermissionTreeInfo> listToPermissionTree(List<PermissionTreeInfo> list) {
        //用递归找子。
        List<PermissionTreeInfo> treeList = new ArrayList<PermissionTreeInfo>();
        for (PermissionTreeInfo tree : list) {
            if (tree.getParentId() == 0) {
                tree.setNumberOfLayers(tree.getNumberOfLayers()+1);
                treeList.add(findPermissionChildren(tree, list));
            }
        }
        return treeList;
    }

    private static PermissionTreeInfo findPermissionChildren(PermissionTreeInfo tree, List<PermissionTreeInfo> list) {
        for (PermissionTreeInfo node : list) {
            if (node.getParentId() == tree.getId()) {
                if (tree.getSysPermissionList() == null) {
                    tree.setSysPermissionList(new ArrayList<PermissionTreeInfo>());
                }
                node.setNumberOfLayers(tree.getNumberOfLayers()+1);
                node.setParentName(tree.getPermissionName());
                tree.getSysPermissionList().add(findPermissionChildren(node, list));
            }
        }
        return tree;
    }
}
