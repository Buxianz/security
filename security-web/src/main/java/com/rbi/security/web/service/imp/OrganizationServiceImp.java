package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.organization.PagingOrganization;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrganizationServiceImp implements OrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImp.class);
    @Autowired
    OrganizationDAO organizationDAO;
    @Override
    public void insertOrganization(SysOrganization sysOrganization) throws RuntimeException {
       try{
               if (sysOrganization.getParentId() == 0) {
                   sysOrganization.setLevel(0);
               } else {
                   if(organizationDAO.getOrganizationById(sysOrganization.getParentId())==null)
                       throw new NonExistentException("父级组织不存在");
                   if(organizationDAO.queryOrganizationInfoByOrganizationNameAndParentId(sysOrganization.getOrganizationName(),sysOrganization.getParentId())==null) {
                       sysOrganization.setLevel(sysOrganization.getParentLevel()+1);
                   }else {
                       throw new RepeatException("添加组织信息重复");
                   }
               }
           organizationDAO.insertOrganization(sysOrganization);
       }catch (RepeatException e) {
           logger.error("添加组织信息重复，组织信息为{}", sysOrganization.toString());
           throw new RuntimeException(e.getMessage());
       }
       catch (NonExistentException e) {
           logger.error("添加组织信息失败,异常为{}", e.getMessage());
           throw new RuntimeException(e.getMessage());
       } catch (Exception e) {
           logger.error("添加组织信息失败，组织信息为{}，异常为{}", sysOrganization.toString(), e);
           throw new RuntimeException("添加组织信息失败");
       }
    }

    @Override
    public void updateOrganization(SysOrganization sysOrganization) throws RuntimeException {
        try{
            if (sysOrganization.getParentId() == 0) {
                sysOrganization.setLevel(0);
            } else {
                if(organizationDAO.getOrganizationById(sysOrganization.getParentId())==null)
                    throw new NonExistentException("父级组织不存在");
                if(organizationDAO.queryOrganizationInfoByOrganizationNameAndParentId(sysOrganization.getOrganizationName(),sysOrganization.getParentId())==null) {
                    sysOrganization.setLevel(sysOrganization.getParentLevel()+1);
                }else {
                    throw new RepeatException("更新组织信息重复");
                }
            }
            organizationDAO.updateOrganization(sysOrganization);
        }catch (RepeatException e) {
            logger.error("更新组织信息重复，组织信息为{}", sysOrganization.toString());
            throw new RuntimeException(e.getMessage());
        }
        catch (NonExistentException e) {
            logger.error("更新组织信息失败,异常为{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            logger.error("更新组织信息失败，组织信息为{}，异常为{}", sysOrganization.toString(), e);
            throw new RuntimeException("更新组织信息失败");
        }
    }

    @Override
    public void deleteOrganization(int id) throws RuntimeException {
        try{
            //删除组织下用户账号的角色关联
            //删除组织下面所有用户账号
            //删除组织下面所有的公司人员信息
            //删除该组织以及其子组织的信息
        } catch (Exception e){
            logger.error("删除组织信息失败，异常为{}",e);
            throw new RuntimeException("删除组织信息失败");
        }
    }

    @Override
    public PageData<PagingOrganization> pagingOrganization(int pageNo,int pageSize ,int startIndex) throws RuntimeException {
        List<PagingOrganization> pagingOrganizations=null;
        try{
             pagingOrganizations=organizationDAO.pagingOrganization(startIndex,pageSize);
            int count =organizationDAO.getOrganizationCount();
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            return new PageData<PagingOrganization>(pageNo,pageSize,totalPage,count,pagingOrganizations);
        }catch (Exception e){
            logger.error("分页获取组织信息失败，异常为{}",e);
            throw new RuntimeException("分页获取组织信息失败");
        }

    }
}
