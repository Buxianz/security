package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.config.PermissionTreeInfo;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.entity.SysSystem;
import com.rbi.security.entity.web.permission.PagingPermission;
import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.TimeStampTool;
import com.rbi.security.web.DAO.SysPermissionDAO;
import com.rbi.security.web.DAO.SystemDAO;
import com.rbi.security.web.service.SysPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class SysPermissionServiceImp implements SysPermissionService {
    private static final Logger logger = LoggerFactory.getLogger(SysPermissionServiceImp.class);
    @Autowired(required = false)
    SysPermissionDAO sysPermissionDAO;
    @Autowired
    SystemDAO systemDAO;

    @Override
    public PageData findSysPermissionByPage(int pageNo, int pageSize) {
        int recNo = pageSize * (pageNo - 1);
        int totalPage = 0;
        int count =0;
        List<SysPermission> sysPermissionList =sysPermissionDAO.findSysPermissionByPage(recNo, pageSize);
        List<PagingPermission> pagingPermissionList =new ArrayList<>();
        for (SysPermission sysPermission:sysPermissionList){
            PagingPermission pagingPermission=new PagingPermission();
            pagingPermission.setDescription(sysPermission.getDescription());
            pagingPermission.setEnabled(sysPermission.getEnabled());
            pagingPermission.setId(sysPermission.getId());
            pagingPermission.setIdt(sysPermission.getIdt());
            pagingPermission.setOperateCode(sysPermission.getOperateCode());
            pagingPermission.setParentId(sysPermission.getParentId());
            pagingPermission.setSystemId(sysPermission.getSystemId());
            pagingPermission.setPermissionName(sysPermission.getPermissionName());
            pagingPermission.setUdt(sysPermission.getUdt());
            pagingPermission.setSystemName(systemDAO.findSystemById(sysPermission.getSystemId()).getSystemName());
            pagingPermissionList.add(pagingPermission);
        }
        count =sysPermissionDAO.findNumSysPermission();
        if (0 == count % pageSize) {
            totalPage = count / pageSize;
        } else {
            totalPage = count / pageSize + 1;
        }
        return new PageData(pageNo, pageSize, totalPage, count, pagingPermissionList);
    }


    @Override
    public PagingPermission findSysPermissionById(Integer id) {
        SysPermission sysPermission=sysPermissionDAO.findSysPermissionById(id);
        PagingPermission pagingPermission=new PagingPermission();
        pagingPermission.setDescription(sysPermission.getDescription());
        pagingPermission.setEnabled(sysPermission.getEnabled());
        pagingPermission.setId(sysPermission.getId());
        pagingPermission.setIdt(sysPermission.getIdt());
        pagingPermission.setOperateCode(sysPermission.getOperateCode());
        pagingPermission.setParentId(sysPermission.getParentId());
        pagingPermission.setPermissionName(sysPermission.getPermissionName());
        pagingPermission.setSystemId(sysPermission.getSystemId());
        pagingPermission.setUdt(sysPermission.getUdt());
        pagingPermission.setSystemName(systemDAO.findSystemById(sysPermission.getSystemId()).getSystemName());
        return pagingPermission;
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
    @Override
    public PageData permissionTreeByPage(int pageNo, int pageSize, int startIndex) throws RuntimeException {
        List<PermissionTreeInfo> permissioonTreeInfoList=null;
        List<PermissionTreeInfo> permissioonTreeInfoList1=null;
        List<SysSystem> syssystemList=null;
        try{
            syssystemList = systemDAO.getAllSystem();
            permissioonTreeInfoList=sysPermissionDAO.pagePermission(startIndex,pageSize);
            int count =sysPermissionDAO.getPermission0Count();
            permissioonTreeInfoList1=sysPermissionDAO.getAllNo0Permission();

            permissioonTreeInfoList.addAll(permissioonTreeInfoList1);
            int totalPage;
            if (count%pageSize==0){
                totalPage = count/pageSize;
            }else {
                totalPage = count/pageSize+1;
            }
            permissioonTreeInfoList=listToRolePermissionTree(permissioonTreeInfoList,syssystemList);
            return new PageData<PermissionTreeInfo>(pageNo,pageSize,totalPage,count,permissioonTreeInfoList);
        }catch (Exception e){
            logger.error("分页获取权限树失败，异常为{}",e);
            throw new RuntimeException("分页获取权限树失败");
        }

    }
    public static List<PermissionTreeInfo> listToRolePermissionTree(List<PermissionTreeInfo> list,List<SysSystem> syssystemList) {
        //用递归找子。
        List<PermissionTreeInfo> treeList = new ArrayList<PermissionTreeInfo>();
        for (PermissionTreeInfo tree : list) {
            if (tree.getParentId() == 0) {
                for (int i=0;i<syssystemList.size();i++){
                    if(tree.getSystemId()==syssystemList.get(i).getId().intValue()){
                        tree.setSystemName(syssystemList.get(i).getSystemName());
                    }
                }
                treeList.add(findRolePermissionChildren(tree, list));
            }
        }
        return treeList;
    }

    private static PermissionTreeInfo findRolePermissionChildren(PermissionTreeInfo tree, List<PermissionTreeInfo> list)
    {
        for (PermissionTreeInfo node : list) {
            if (node.getParentId() == tree.getId()) {
                node.setParentName(tree.getPermissionName());
                node.setSystemName(tree.getSystemName());
                if (tree.getSysPermissionList() == null) {
                    tree.setSysPermissionList(new ArrayList<PermissionTreeInfo>());
                }
                tree.getSysPermissionList().add(findRolePermissionChildren(node, list));
            }
        }
        return tree;
    }
}
