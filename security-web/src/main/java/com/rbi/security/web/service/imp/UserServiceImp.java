package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.entity.SysUserRole;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.LocalDateUtils;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.Tools;
import com.rbi.security.web.DAO.CompanyPersonnelDAO;
import com.rbi.security.web.DAO.OrganizationDAO;
import com.rbi.security.web.DAO.SysUSerDAO;
import com.rbi.security.web.DAO.SysUserRoleDAO;
import com.rbi.security.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: 用户管理模块
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
public class UserServiceImp implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    SysUSerDAO sysUSerDAO;
    @Autowired
    SysUserRoleDAO sysUserRoleDAO;
    @Autowired
    OrganizationDAO organizationDAO;
    @Autowired
    CompanyPersonnelDAO companyPersonnelDAO;
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertUser(SysUser sysUser) throws RuntimeException {
        try {
           Integer companyPersonnelId= companyPersonnelDAO.getPersonnelByIdCardNo(sysUser.getIdCardNo());
           if(companyPersonnelId==null){
            throw new NonExistentException("身份证不存在");
           }
            Subject subject = SecurityUtils.getSubject();
            String idt = LocalDateUtils.localDateTimeFormat(LocalDateTime.now(), LocalDateUtils.FORMAT_PATTERN);
            String salt = Tools.uuid();
            sysUser.setUsername(sysUser.getIdCardNo().substring(sysUser.getIdCardNo().length()-8));
            sysUser.setPassword(sysUser.getIdCardNo().substring(sysUser.getIdCardNo().length()-8));
            sysUser.setCompanyPersonnelId(companyPersonnelId);
            Md5Hash md5Hash=new Md5Hash(sysUser.getPassword(),salt,2);
            sysUser.setPassword(md5Hash.toString());
            sysUser.setSalf(salt);
            sysUser.setOperatingStaff(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
            sysUser.setIdt(idt);
            if (sysUSerDAO.increaseDuplicateCheck(sysUser.getUsername()) == null) {
                sysUSerDAO.insertUser(sysUser);

                if (sysUser.getSysUserRoleList().size() != 0) {
                    for (int i=0;i<sysUser.getSysUserRoleList().size();i++){
                        sysUser.getSysUserRoleList().get(i).setOperatingStaff(sysUser.getOperatingStaff());
                        sysUser.getSysUserRoleList().get(i).setUserId(sysUser.getId());
                        sysUser.getSysUserRoleList().get(i).setIdt(idt);
                    }
                    sysUserRoleDAO.inserUserRoleInfo(sysUser.getSysUserRoleList());
                }
            } else throw new RepeatException("添加用户信息重复");
        } catch (RepeatException e) {
            logger.error("添加用户信息重复，用户信息为{}", sysUser.toString());
            throw new RuntimeException(e.getMessage());
        }
        catch (NonExistentException e) {
            logger.error("添加用户信息失败，异常为{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            logger.error("添加用户信息失败，用户信息为{}，异常为{}", sysUser.toString(), e);
            throw new RuntimeException("添加用户信息失败");
        }

    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserInfo(SysUser sysUser) throws RuntimeException {
        try{
            if (sysUSerDAO.updateDuplicateCheck(sysUser)==null) {
                sysUSerDAO.updateUser(sysUser);
                sysUserRoleDAO.updateUserRoleInfo(sysUser.getSysUserRoleList());
            }
            else throw new RepeatException("更新用户信息重复");
        }catch (RepeatException e){
            logger.error("更新用户信息重复，用户信息为{}",sysUser.toString());
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e){
            logger.error("更新用户信息失败，用户信息为{}，异常为{}",sysUser.toString(),e);
            throw new RuntimeException("更新用户信息失败");
        }
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteUser(int id) throws RuntimeException {
        try{
            sysUSerDAO.deleteUserById(id);
        }
        catch (Exception e){
            logger.error("删除用户信息失败，异常为{}",e);
            throw new RuntimeException("删除用户信息失败");
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PageData<PagingUser> pagingQueryUserInfo(int pageNo,int pageSize ,int startIndex) throws RuntimeException {
        List<PagingUser> pagingUserList=null;
        List<PagingUser> newPagingUserList=null;
        try{
            pagingUserList=sysUSerDAO.getAllUserInfo();
            newPagingUserList=processingPagingData(pagingUserList,pageNo,pageSize,startIndex);
            int count =pagingUserList.size();
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            pagingUserList= setUserOrganization(pagingUserList);
            return new PageData<PagingUser>(pageNo,pageSize,totalPage,count,newPagingUserList);
        }catch (Exception e){
            logger.error("分页获取用户信息失败，异常为{}",e);
            throw new RuntimeException("分页获取用户信息失败");
        }
    }
    //处理数据，通过代码进行分页操作
    private List<PagingUser> processingPagingData(List<PagingUser> pagingUserList,int pageNo,int pageSize ,int startIndex){
            List<PagingUser> pagingUsers =null;
            pagingUsers = new LinkedList<PagingUser>();
            int endIndex=0;
        //执行筛选操作，降不符合规则的remove掉
        Subject subject = SecurityUtils.getSubject();
        AuthenticationUserDTO currentUser= (AuthenticationUserDTO)subject.getPrincipal();
        //。。。。。。。。。。。。。
        for(int i=0;i<pagingUserList.size();i++){
            if(pagingUserList.get(i).getUsername().equals(currentUser.getUsername())){
                pagingUserList.remove(i);
                break;
            }
        }
            if(pagingUserList.size()-startIndex>=pageSize){
                endIndex=startIndex+pageSize;
            }else{
                endIndex=pagingUserList.size()-startIndex;
            }
             //将符合规格的添加到
            for(int i=startIndex;i<endIndex;i++){
                pagingUsers.add(pagingUserList.get(i));
            }
        return pagingUsers;
    }
    //整合数据，获取各条用户信息所在的组织
    private List<PagingUser> setUserOrganization(List<PagingUser> pagingUserList) throws RuntimeException{
        try {
            List<Integer> userIds=new LinkedList<Integer>();
            for(int i=0;i<pagingUserList.size();i++){
                userIds.add(pagingUserList.get(i).getId());
            }
            List<SysUserRole> sysUserRoleList=null;
            if(userIds.size()!=0)
            sysUserRoleList=sysUserRoleDAO.getUserRole(userIds);
            pagingUserList.forEach(pagingUser ->{
                //此处可优化为放在redis
                List<SysOrganization> organizationList = organizationDAO.queryAllParentDate(pagingUser.getOrganizationId());
                organizationList.forEach(sysOrganization -> {
                    switch (sysOrganization.getLevel()) {
                        case 1: {
                            pagingUser.setCompanyId(sysOrganization.getId());
                            pagingUser.setCompanyName(sysOrganization.getOrganizationName());
                        }break;
                        case 2:{
                            pagingUser.setFactoryId(sysOrganization.getId());
                            pagingUser.setFactoryName(sysOrganization.getOrganizationName());
                        }break;
                        case 3:{
                            pagingUser.setWorkshopId(sysOrganization.getId());
                            pagingUser.setWorkshopName(sysOrganization.getOrganizationName());
                        }break;
                        case 4:{
                            pagingUser.setTeamId(sysOrganization.getId());
                            pagingUser.setTeamName(sysOrganization.getOrganizationName());
                        }break;
                    }
                });
            });
            for(int i=0;i<pagingUserList.size();i++){
                for(int j=0;j<sysUserRoleList.size();j++){
                    if(sysUserRoleList.get(j).getUserId()==pagingUserList.get(i).getId()){
                        if(pagingUserList.get(i).getSysUserRoleList()==null){
                            pagingUserList.get(i).setSysUserRoleList(new LinkedList<SysUserRole>());
                        }
                        pagingUserList.get(i).setRoleName(sysUserRoleList.get(j).getRoleName());
                        pagingUserList.get(i).getSysUserRoleList().add(sysUserRoleList.get(j));
                    }
                }
            }
        }catch (Exception e){
            logger.error("获取用户组织信息失败，异常为{}",e);
            throw new RuntimeException();
        }
        return pagingUserList;
    }


}
