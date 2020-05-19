package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.permission.PagingPermission;
import com.rbi.security.tool.EncapsulationTreeUtil;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.TimeStampTool;
import com.rbi.security.web.DAO.SysPermissionDAO;
import com.rbi.security.web.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class SysPermissionServiceImp implements SysPermissionService {

    @Autowired(required = false)
    SysPermissionDAO sysPermissionDAO;

    @Override
    public List<SysPermission> findSysPermissionAll() {
        try {
            List<SysPermission> sysPermissionList = sysPermissionDAO.findSysPermissionAll();
            return EncapsulationTreeUtil.getTree(sysPermissionList,"id","parentId","pagingPermissions");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PageData findSysPermissionByPage(int pageNo, int pageSize) {
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        int count =0;
        List<SysPermission> sysPermissionList =sysPermissionDAO.findSysPermissionByPage(recNo, pageSize);
        count =sysPermissionDAO.findNumSysPermission();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, sysPermissionList);
    }

    @Override
    public SysPermission findSysPermissionById(Integer id) {
        SysPermission sysPermission=sysPermissionDAO.findSysPermissionById(id);
        return sysPermission;
    }

    @Override
    public void insertSysPermission(JSONObject json) {
//        SysPermission sysPermission = JSONObject.parseObject(json.toJSONString(),SysPermission.class);
        String permissionName=json.getString("permissionName");
        String operateCode=json.getString("operateCode");
        Integer parentId=json.getInteger("parentId");
        String description=json.getString("description");
        Integer systemId=json.getInteger("systemId");
        Integer enabled=json.getInteger("enabled");
        String idt= null;
        try {
            idt = TimeStampTool.dateTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String udt=idt;

        sysPermissionDAO.insertSysPermission(permissionName,operateCode,parentId,description,systemId,enabled,idt,udt);
    }

    @Override
    public String deleteSysPermissionById(JSONArray result) {
        String reT=null;
        for (int i=0;i<result.size();i++) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result.get(i));
            Integer Id = jsonObject.getInteger("id");
            if (sysPermissionDAO.findSysPermissionById(Id)!=null){
                sysPermissionDAO.deleteSysPermissionById(Id);
                reT="1000";
            }else {
                return String.valueOf(Id);
            }
        }
        return reT;
    }

    @Override
    public String updateSysPermission(JSONObject json) {
        Integer id=json.getInteger("id");
        if (sysPermissionDAO.findSysPermissionById(id)!=null) {
            String permissionName = json.getString("permissionName");
            String operateCode = json.getString("operateCode");
            Integer parentId = json.getInteger("parentId");
            String description = json.getString("description");
            Integer systemId = json.getInteger("systemId");
            Integer enabled = json.getInteger("enabled");
            String udt = null;
            try {
                udt = TimeStampTool.dateTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            sysPermissionDAO.updateSysPermission(id, permissionName, operateCode, parentId, description, systemId, enabled, udt);
            return "1000";
        }else {
            return "1006";
        }
    }
}
