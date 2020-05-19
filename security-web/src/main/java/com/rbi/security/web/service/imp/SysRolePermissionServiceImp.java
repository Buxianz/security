package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.entity.web.permission.PagingPermission;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.SysPermissionDAO;
import com.rbi.security.web.DAO.SysRolePermissionDAO;
import com.rbi.security.web.service.SysRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysRolePermissionServiceImp implements SysRolePermissionService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImp.class);

    @Autowired(required = false)
    SysRolePermissionDAO sysRolePermissionDAO;

    @Autowired(required = false)
    SysPermissionDAO sysPermissionDAO;



    @Override
    public PageData findSysRolePermissionByPage(int pageNo, int pageSize) {
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        int count = 0;
//        List<PagingPermission> pagingPermissions =new ArrayList<>();
//        List<SysRolePermission> sysRolePermissionList =sysRolePermissionDAO.findSysRolePermissionByPage(recNo, pageSize);
//        int i=0;
//        for (SysRolePermission sysRolePermission:sysRolePermissionList){
//            PagingPermission pagingPermission=new PagingPermission();
//            SysPermission sysPermission=sysPermissionDAO.findSysPermissionById(sysRolePermission.getPermissionId());
////            SysRole sysRole=sysPermissionDAO.findSysPermissionById(sysRolePermission.getPermissionId());
//            pagingPermission.setSysPermission(sysPermission);
//            pagingPermissions.set(i,pagingPermission);
//            i++;
//            count +=1;
//        }
        try {
            List<PagingPermission> pagingPermissions =new ArrayList<>();
            pagingPermissions=sysRolePermissionDAO.findSysRolePermissionByPage(recNo, pageSize);
            count = sysRolePermissionDAO.findNumSysRolePermission();
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, pagingPermissions);
        } catch (Exception e) {
            logger.error("失败，异常为{}", e);
            throw new RuntimeException("失败");
        }
    }

    @Override
    public SysRolePermission findSysRolePermissionById(Integer id) {
        SysRolePermission sysRolePermission=sysRolePermissionDAO.findSysRolePermissionById(id);
        return sysRolePermission;
    }

    @Override
    public void insertSysRolePermission(JSONObject json) {
        Integer roleId = json.getInteger("roleId");
//        for (int i=0;i<result.size();i++) {
//            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(1));
            Integer permissionId = json.getInteger("permissionId");
            sysRolePermissionDAO.insertSysRolePermission(permissionId,roleId);
//        }
    }

    @Override
    public String deleteSysRolePermissionById(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (sysRolePermissionDAO.findSysRolePermissionById(Id)!=null){
                sysRolePermissionDAO.deleteSysRolePermissionById(Id);
                reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }

    @Override
    public String updateSysRolePermission(JSONObject json) {
        Integer id=json.getInteger("id");
        if (sysRolePermissionDAO.findSysRolePermissionById(id)!=null) {
            Integer permissionId = json.getInteger("permissionId");
            Integer roleId = json.getInteger("roleId");

            sysRolePermissionDAO.updateSysRolePermission(id, permissionId, roleId);
            return "1000";
        }else {
            return "1006";
        }
    }
}
