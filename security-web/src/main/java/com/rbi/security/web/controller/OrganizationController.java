package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.organization.PagingOrganization;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;
    @RequestMapping("/insertOrganization")
    public ResponseModel insertOrganization(@RequestBody JSONObject date){
        try{
            SysOrganization sysOrganization= JSONObject.parseObject(date.toJSONString(), SysOrganization.class);
            organizationService.insertOrganization(sysOrganization);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/updateOrganization")
    public ResponseModel updateOrganization(@RequestBody JSONObject date){
        try{
            SysOrganization sysOrganization= JSONObject.parseObject(date.toJSONString(), SysOrganization.class);
            organizationService.updateOrganization(sysOrganization);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/deleteOrganization")
    //@RequiresPermissions("user:del")
    public ResponseModel deleteOrganization(@RequestBody JSONObject date){
        Integer id =date.getInteger("id");
        try{
            organizationService.deleteOrganization(id);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/pageQueryUserInfo")
    public ResponseModel<PageData<PagingOrganization>> getPageQueryUserInfo(@RequestBody JSONObject date){

        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingOrganization> data=organizationService.pagingOrganization(pageNo,pageSize,startIndex);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
