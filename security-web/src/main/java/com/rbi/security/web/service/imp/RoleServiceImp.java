package com.rbi.security.web.service.imp;

import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysRole;

import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.entity.web.role.RolePermissioonInfo;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.exception.RepeatException;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.SysRoleDAO;
import com.rbi.security.web.DAO.SysRolePermissionDAO;
import com.rbi.security.web.service.RoleService;
import com.rbi.security.web.shiro.MyShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: 角色管理模块
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
public class RoleServiceImp implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImp.class);
    @Autowired
    SysRoleDAO sysRoleDAO;
    @Autowired
    SysRolePermissionDAO sysRolePermissionDAO;
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void insertRole(SysRole sysRole) throws RuntimeException {
        try{
            Subject subject = SecurityUtils.getSubject();
            if(sysRoleDAO.getRoleByName(sysRole.getRoleName())==null) {
                sysRole.setOperatingStaff(((AuthenticationUserDTO)subject.getPrincipal()).getCompanyPersonnelId());
                sysRoleDAO.insertRole(sysRole);
                //添加角色对应的权限
                for(int i=0;i<sysRole.getSysRolePermissionList().size();i++){
                    sysRole.getSysRolePermissionList().get(i).setRoleId(sysRole.getId());
                }
                if(sysRole.getSysRolePermissionList().size()!=0)
                    sysRolePermissionDAO.insertRolePermissions(sysRole.getSysRolePermissionList());
            }
            else  throw new RepeatException("加角色信息重复");
        }catch (RepeatException e) {
            logger.error("添加角色信息重复，角色信息为{}", sysRole.toString());
            throw new RuntimeException(e.getMessage());
        }
        catch (NonExistentException e) {
            logger.error("添加角色信息失败,异常为{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            logger.error("添加角色信息失败，角色信息为{}，异常为{}", sysRole.toString(), e);
            throw new RuntimeException("添加角色信息失败");
        }
    }
    @Autowired
    RedisCacheManager redisCacheManager;
    @Autowired
    MyShiroRealm myShiroRealm;
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateRole(SysRole sysRole) throws RuntimeException {
        try{
            if(sysRoleDAO.updateDuplicateJudgement(sysRole)==null) {
                //更新角色信息
                sysRoleDAO.updateRoleById(sysRole);
                for(int i=0;i<sysRole.getSysRolePermissionList().size();i++){
                    sysRole.getSysRolePermissionList().get(i).setRoleId(sysRole.getId());
                }
                //删除原有的角色权限关联
                sysRolePermissionDAO.deleteRolePermissionByRoleId(sysRole.getId());
                //添加现有的权限关联
                if(sysRole.getSysRolePermissionList().size()!=0){
                    sysRolePermissionDAO.insertRolePermissions(sysRole.getSysRolePermissionList());
                }
               System.out.println(myShiroRealm.getKey(SecurityUtils.getSubject()));
                 ((RedisCache)redisCacheManager.getCache("com.rbi.security.web.shiro.MyShiroRealm.authorizationCache")).clear();
            }else {
                throw new RepeatException("数据已存在");
            }
        }catch (RepeatException e) {
            logger.error("更新角色信息重复，角色信息为{}", sysRole.toString());
            throw new RuntimeException(e.getMessage());
        }
        catch (NonExistentException e) {
            logger.error("更新角色信息失败,异常为{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            logger.error("更新角色信息失败，角色信息为{}，异常为{}", sysRole.toString(), e);
            throw new RuntimeException("更新角色信息失败");
        }
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteRole(int id) throws RuntimeException {
        try{

        } catch (Exception e){
            logger.error("删除角色信息失败，异常为{}",e);
            throw new RuntimeException("删除角色信息失败");
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PageData<PagingRole> pagingRole(int pageNo, int pageSize, int startIndex) throws RuntimeException {
        List<PagingRole> pagingRoleList=null;
        try{
            List<Integer> roleIds=new LinkedList<Integer>();
            pagingRoleList=sysRoleDAO.pagingRole(startIndex,pageSize);
            int count =sysRoleDAO.getRoleCount();
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            for (PagingRole pr:pagingRoleList) {
                roleIds.add(pr.getId());
            }
            //获取角色对应的权限集合
            List<RolePermissioonInfo> rolePermissionInfos=null;
            if(roleIds.size()!=0) {
                rolePermissionInfos = sysRolePermissionDAO.getRolePermissionByRoleIds(roleIds);
                rolePermissionInfos = listToRolePermissionTree(rolePermissionInfos);
            }
            for(int i=0;i<pagingRoleList.size();i++){
                for(int j=0;j<rolePermissionInfos.size();j++){
                    if(pagingRoleList.get(i).getId()==rolePermissionInfos.get(j).getRoleId()){
                        if(pagingRoleList.get(i).getRolePermissionInfoList()==null){
                            pagingRoleList.get(i).setRolePermissionInfoList(new ArrayList<RolePermissioonInfo>());
                        }
                        pagingRoleList.get(i).getRolePermissionInfoList().add(rolePermissionInfos.get(j));
                    }
                }
            }
            return new PageData<PagingRole>(pageNo,pageSize,totalPage,count,pagingRoleList);
        }catch (Exception e){
            logger.error("分页获取角色信息失败，异常为{}",e);
            throw new RuntimeException("分页获取角色信息失败");
        }
    }
    private static List<RolePermissioonInfo> listToRolePermissionTree(List<RolePermissioonInfo> list) {
        //用递归找子。
        List<RolePermissioonInfo> treeList = new ArrayList<RolePermissioonInfo>();
        for (RolePermissioonInfo tree : list) {
            if (tree.getParentId().intValue() == 0) {
                treeList.add(findRolePermissionChildren(tree, list));
            }
        }
        return treeList;
    }

    private static RolePermissioonInfo findRolePermissionChildren(RolePermissioonInfo tree, List<RolePermissioonInfo> list)
    {
        for (RolePermissioonInfo node : list) {
            if (node.getParentId().intValue() == tree.getPermissionId().intValue() && node.getRoleId().intValue()==tree.getRoleId().intValue()) {
                if (tree.getRolePermissionInfos() == null) {
                    tree.setRolePermissionInfos(new ArrayList<RolePermissioonInfo>());
                }
                tree.getRolePermissionInfos().add(findRolePermissionChildren(node, list));
            }
        }
        return tree;
    }
}
