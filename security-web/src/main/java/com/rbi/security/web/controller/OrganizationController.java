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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
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
@RestController
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;

    @RequiresPermissions("organization:add")
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
    @RequiresPermissions("organization:update")
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
    @RequiresPermissions("organization:delete")
    public ResponseModel deleteOrganization(@RequestBody JSONObject date){
        Integer id =date.getInteger("id");
        try{
            organizationService.deleteOrganization(id);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/pageOrganization")
    @RequiresPermissions("organization:page")
    public ResponseModel<PageData<PagingOrganization>> getPageOrganization(@RequestBody JSONObject date){
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
