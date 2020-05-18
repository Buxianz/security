package com.rbi.security.web.service.imp;

import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.SysUSerDAO;
import com.rbi.security.web.DAO.SysUserRoleDAO;
import com.rbi.security.web.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImp implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    SysUSerDAO sysUSerDAO;
    @Autowired
    SysUserRoleDAO sysUserRoleDAO;
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertUser(SysUser sysUser) throws RuntimeException {
        try {
            if (sysUSerDAO.increaseDuplicateCheck(sysUser.getUsername()) == null) {
                sysUSerDAO.insertUser(sysUser);
                if (sysUser.getSysUserRoleList().size() != 0) {
                    sysUserRoleDAO.inserUserRoleInfo(sysUser.getSysUserRoleList());
                }
            } else throw new RepeatException("添加用户信息重复");
        } catch (RepeatException e) {
            logger.error("添加用户信息重复，用户信息为{}", sysUser.toString());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            logger.error("添加用户信息失败，用户信息为{}，异常为{}", sysUser.toString(), e);
            throw new RuntimeException("添加用户信息失败");
        }

    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateUserInfo(SysUser sysUser) throws RuntimeException {
        try{
            if (sysUSerDAO.updateDuplicateCheck(sysUser)==null)
                sysUSerDAO.updateUser(sysUser);
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


        int count = sysUSerDAO.getUserCount();
        int totalPage;
        if (count%pageSize==0){
            totalPage = count/pageSize;
        }else {
            totalPage = count/pageSize+1;
        }
        return new PageData<PagingUser>(pageNo,pageSize,totalPage,count,pagingUserList);
    }
}
