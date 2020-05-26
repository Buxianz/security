package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.organization.PagingOrganization;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.service.OrganizationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: 组织管理模块
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
public class OrganizationServiceImp implements OrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImp.class);
    @Autowired
    OrganizationDAO organizationDAO;
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertOrganization(SysOrganization sysOrganization) throws RuntimeException {
       try{
               if (sysOrganization.getParentId() == 0) {
                   sysOrganization.setLevel(1);
               } else {
                   if(organizationDAO.getOrganizationById(sysOrganization.getParentId())==null)
                       throw new NonExistentException("父级组织不存在");
                   sysOrganization.setLevel(sysOrganization.getParentLevel()+1);
               }
           if(organizationDAO.queryOrganizationInfoByOrganizationNameAndParentId(sysOrganization.getOrganizationName(),sysOrganization.getParentId())!=null)
               throw new RepeatException("添加组织信息重复");
           Subject subject = SecurityUtils.getSubject();
           sysOrganization.setOperatingStaff(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateOrganization(SysOrganization sysOrganization) throws RuntimeException {
        try{
            if (sysOrganization.getParentId() == 0) {
                sysOrganization.setLevel(1);
            } else {
                if(organizationDAO.getOrganizationById(sysOrganization.getParentId())==null)
                    throw new NonExistentException("父级组织不存在");
                sysOrganization.setLevel(sysOrganization.getParentLevel()+1);
            }
            if(organizationDAO.updateDuplicateJudgement(sysOrganization.getOrganizationName(),sysOrganization.getParentId(),sysOrganization.getId())!=null)
                 throw new RepeatException("更新组织信息重复");
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
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
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
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
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
