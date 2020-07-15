package com.rbi.security.web.service.imp;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.config.PermissionTreeInfo;
import com.rbi.security.entity.config.SystemMenuPermisson;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.entity.SysSystem;
import com.rbi.security.entity.web.safe.demand.SafaTrainingType;
import com.rbi.security.entity.web.system.SystemCategory;
import com.rbi.security.entity.web.user.CompanyPersonnelBox;
import com.rbi.security.tool.TreeDTO;
import com.rbi.security.web.DAO.*;
import com.rbi.security.web.DAO.safe.SafaTrainingTypeDAO;
import com.rbi.security.web.DAO.system.SystemCategoryDAO;
import com.rbi.security.web.service.ConfigService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: 配置信息管理模块
 * @USER: "吴松达"
 * @DATE: 2020/5/21
 * @TIME: 17:44
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 星期四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 44
 * @PROJECT_NAME: security
 **/
@Service
public class ConfigServiceImp implements ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImp.class);
@Autowired
    OrganizationDAO organizationDAO;
@Autowired
    SysRoleDAO sysRoleDAO;
@Autowired
    CompanyPersonnelDAO companyPersonnelDAO;
@Autowired
    SystemDAO systemDAO;
@Autowired
    SysPermissionDAO sysPermissionDAO;
@Autowired
    SystemCategoryDAO systemCategoryDAO;
@Autowired
    SafaTrainingTypeDAO safaTrainingTypeDAO;
    @Override
    public List<OrganizationTree> getOrganizationTree() throws RuntimeException {
        List<OrganizationTree> organizationTreeList=null;
        try {
            organizationTreeList= organizationDAO.getAllOrganization();
            organizationTreeList= TreeDTO.listToTree(organizationTreeList);
            return organizationTreeList;
        }catch (Exception e){
            logger.error("获取组织树信息失败，异常为{}",e);
            throw new RuntimeException("获取组织树信息失败");
        }
    }

    @Override
    public List<SysRole> getRole() throws RuntimeException {
        List<SysRole> sysRoleList=null;
        try{
            sysRoleList=sysRoleDAO.getALlRole();
        }catch (Exception e){
            logger.error("获取角色信息失败，异常为{}",e);
            throw new RuntimeException("获取角色信息失败");
        }
        return sysRoleList;
    }

    @Override
    public List<OrganizationTree> getCompanyPersonnelTree() throws RuntimeException {
        List<OrganizationTree> organizationTreeList=null;
        List<CompanyPersonnelBox> companyPersonnelBoxList=null;
        try {
            organizationTreeList= organizationDAO.getAllOrganization();
            organizationTreeList= TreeDTO.listToTree(organizationTreeList);
            companyPersonnelBoxList= companyPersonnelDAO.getAllCompanyPersonnel();
            ergodicCompanyPersonnelTree(organizationTreeList,companyPersonnelBoxList);
            return organizationTreeList;
        }catch (Exception e){
            logger.error("获取组织树信息失败，异常为{}",e);
            throw new RuntimeException("获取组织树信息失败");
        }
    }
    private void ergodicCompanyPersonnelTree(List<OrganizationTree> organizationTreeList,List<CompanyPersonnelBox> companyPersonnelBoxList){
        for(int i=0;i<organizationTreeList.size();i++){
            if(organizationTreeList.get(i).getParentId()==0){
                for(int j=0;j<companyPersonnelBoxList.size();j++){
                    if(organizationTreeList.get(i).getId()==companyPersonnelBoxList.get(j).getOrganizationId()){
                          if(organizationTreeList.get(i).getCompanyPersonnelBoxList()==null){
                              organizationTreeList.get(i).setCompanyPersonnelBoxList(new LinkedList<CompanyPersonnelBox>());
                          }
                        organizationTreeList.get(i).getCompanyPersonnelBoxList().add(companyPersonnelBoxList.get(j));
                    }
                }
                if(organizationTreeList.get(i).getChiled()!=null){
                    ergodicCompanyPersonnelChilrdTree(organizationTreeList.get(i).getChiled(),companyPersonnelBoxList);
                }
            }
        }
    }
    private void ergodicCompanyPersonnelChilrdTree(List<OrganizationTree> chilrd,List<CompanyPersonnelBox> companyPersonnelBoxList){
            for(int i=0;i<chilrd.size();i++){
                for(int j=0;j<companyPersonnelBoxList.size();j++){
                    if(chilrd.get(i).getId()==companyPersonnelBoxList.get(j).getOrganizationId()){
                        if(chilrd.get(i).getCompanyPersonnelBoxList()==null){
                            chilrd.get(i).setCompanyPersonnelBoxList(new LinkedList<CompanyPersonnelBox>());
                        }
                        chilrd.get(i).getCompanyPersonnelBoxList().add(companyPersonnelBoxList.get(j));
                    }
                }
               if (chilrd.get(i).getChiled()!=null){
                   ergodicCompanyPersonnelChilrdTree(chilrd.get(i).getChiled(),companyPersonnelBoxList);
               }
            }

    }
    /**
     * 获取系统权限树
     */
    public List<SystemMenuPermisson> getSystemMenuPermissonTree() throws RuntimeException{
        List<SystemMenuPermisson> systemMenuPermissonList=new ArrayList<SystemMenuPermisson>();
        List<SysSystem> syssystemList=null;
        List<PermissionTreeInfo> permissionTreeInfoList=null;
        try{
            syssystemList = systemDAO.getAllSystem();
            for(int i=0;i<syssystemList.size();i++){
                systemMenuPermissonList.add(new SystemMenuPermisson(syssystemList.get(i).getId(),syssystemList.get(i).getSystemName()));
            }
            permissionTreeInfoList=sysPermissionDAO.getAllSsytemPermission();
            permissionTreeInfoList=listToPermissionTree(permissionTreeInfoList);
            for (SystemMenuPermisson rpc:systemMenuPermissonList) {
                for (PermissionTreeInfo item:permissionTreeInfoList) {
                    if(item.getSystemId()==rpc.getId()){
                        item.setSystemName(rpc.getSystemName());
                        if(rpc.getPermissionTreeInfoList()==null){
                            rpc.setPermissionTreeInfoList(new LinkedList<PermissionTreeInfo>());
                        }
                        rpc.getPermissionTreeInfoList().add(item);
                    }
                }
            }

        }catch (Exception e){
            logger.error("获取权限树信息失败，异常为{}",e);
            throw new RuntimeException("获取权限树信息失败");
        }
        return systemMenuPermissonList;
    }


    private   List<PermissionTreeInfo> listToPermissionTree(List<PermissionTreeInfo> list) {
        //用递归找子。
        List<PermissionTreeInfo> treeList = new ArrayList<PermissionTreeInfo>();
        for (PermissionTreeInfo tree : list) {
            if (tree.getParentId() == 0) {
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
                node.setParentName(tree.getPermissionName());
                tree.getSysPermissionList().add(findPermissionChildren(node, list));
            }
        }
        return tree;
    }
    /**
     * 获取制度类型下拉框信息
     */
    public List<SystemCategory> getSystemCategoryBox() throws RuntimeException {
        List<SystemCategory> systemCategories=null;
        try{
            systemCategories=systemCategoryDAO.getAllSystemCategory();
        }catch (Exception e){
            logger.error("获取制度类型下拉框信息失败，异常为{}",e);
            throw new RuntimeException("获取制度类型下拉框信息");
        }
        return systemCategories;
    }

    /**
     * 获取所有培训类型下拉框
     */
    public List<SafaTrainingType> getSafaTrainingType() throws RuntimeException {
        List<SafaTrainingType> safaTrainingTypeList=null;
        try{
            safaTrainingTypeList=safaTrainingTypeDAO.getAllSafaTrainingType();
        }catch (Exception e){
            logger.error("获取所有培训内容下拉框信息失败，异常为{}",e);
            throw new RuntimeException("获取所有培训内容下拉框失败");
        }
        return safaTrainingTypeList;
    }
}
