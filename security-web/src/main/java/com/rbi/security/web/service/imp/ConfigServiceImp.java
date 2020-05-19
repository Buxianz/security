package com.rbi.security.web.service.imp;

import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.user.CompanyPersonnelBox;
import com.rbi.security.tool.TreeDTO;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.DAO.SysRoleDAO;
import com.rbi.security.web.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class ConfigServiceImp implements ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImp.class);
@Autowired
    OrganizationDAO organizationDAO;
@Autowired
    SysRoleDAO sysRoleDAO;
@Autowired
    CompanyPersonnelDAO companyPersonnelDAO;
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
            ergodicTree(organizationTreeList,companyPersonnelBoxList);
            return organizationTreeList;
        }catch (Exception e){
            logger.error("获取组织树信息失败，异常为{}",e);
            throw new RuntimeException("获取组织树信息失败");
        }
    }
    private void ergodicTree(List<OrganizationTree> organizationTreeList,List<CompanyPersonnelBox> companyPersonnelBoxList){
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
                    ergodicChilrdTree(organizationTreeList.get(i).getChiled(),companyPersonnelBoxList);
                }
            }
        }
    }
    private void ergodicChilrdTree(List<OrganizationTree> chilrd,List<CompanyPersonnelBox> companyPersonnelBoxList){
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
                   ergodicChilrdTree(chilrd.get(i).getChiled(),companyPersonnelBoxList);
               }
            }

    }
}
