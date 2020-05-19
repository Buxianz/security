package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.entity.web.permission.PagingPermission;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.tool.PageData;
import com.rbi.security.web.DAO.SysPermissionDAO;
import com.rbi.security.web.DAO.SysRolePermissionDAO;
import com.rbi.security.web.service.SysRolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
            List<SysRolePermission> sysRolePermissionList =sysRolePermissionDAO.findSysRolePermissionByPage(recNo, pageSize);
            count = sysRolePermissionDAO.findNumSysRolePermission();
            if (0 == count % pageSize) {
                totalPage = count / pageSize;
            } else {
                totalPage = count / pageSize + 1;
            }
            return new PageData(pageNo, pageSize, totalPage, count, sysRolePermissionList);
    }

    @Override
    public SysRolePermission findSysRolePermissionById(Integer id) {
        SysRolePermission sysRolePermission=sysRolePermissionDAO.findSysRolePermissionById(id);
        return sysRolePermission;
    }

    @Override
    public PageData findSysPermissionByRoleCode(Integer RoleId, int pageNo, int pageSize) {
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        int count = 0;
        List<SysRolePermission> sysRolePermissionList=sysRolePermissionDAO.findSysRolePermissionList();
        List<SysPermission> newsysPermissionList=processingPagingData(sysRolePermissionList,RoleId,pageSize,recNo);
        count =newsysPermissionList.size();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, newsysPermissionList);
    }
    //处理数据，通过代码进行分页操作
    private List<SysPermission> processingPagingData(List<SysRolePermission> sysRolePermissionList,Integer RoleId,int pageSize ,int startIndex){
        List<SysRolePermission> sysRolePermissionList1 =null;
        int endIndex=0;
        int j=0;
        for(int i=0;i<sysRolePermissionList.size();i++){
            if(sysRolePermissionList.get(i).getRoleId()==RoleId){
                SysRolePermission sysRolePermission=sysRolePermissionList.get(i);
                sysRolePermissionList1.add(j,sysRolePermission);
                j++;
            }
        }
        List<SysPermission> sysPermissionList=sysPermissionDAO.findSysPermissionAll();
        List<SysPermission> newsysPermissionList=new ArrayList<>();
        for(int i=0;i<sysRolePermissionList.size();i++){
            for (int m=0;m<sysRolePermissionList.size();m++)
            if(sysPermissionList.get(i).getId()==sysRolePermissionList.get(m).getPermissionId()){
                newsysPermissionList.add(sysPermissionList.get(i));
                break;
            }
        }

        List<SysPermission> newsysPermissionList1=new ArrayList<>();
        if(newsysPermissionList.size()-startIndex>=pageSize){
            endIndex=startIndex+pageSize;
        }else{
            endIndex=newsysPermissionList.size()-startIndex;
        }
        //将符合规格的添加到
        for(int i=startIndex;i<endIndex;i++){
            newsysPermissionList1.add(newsysPermissionList.get(i));
        }
        return newsysPermissionList1;
    }

    @Override
    public void insertSysRolePermission(JSONObject json,JSONArray result) {
        Integer roleId = json.getInteger("roleId");
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer permissionId = jsonObject.getInteger("permissionId");
            sysRolePermissionDAO.insertSysRolePermission(permissionId,roleId);
        }
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
