package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysOrganization;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.organization.PagingOrganization;
import com.rbi.security.entity.web.role.PagingRole;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
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
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;
    @RequestMapping("/insertRole")
    public ResponseModel insertRole(@RequestBody JSONObject date){
        try{
            SysRole sysRole= JSONObject.parseObject(date.toJSONString(), SysRole.class);
            roleService.insertRole(sysRole);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/updateRole")
    public ResponseModel updateRole(@RequestBody JSONObject date){
        try{

            SysRole sysRole= JSONObject.parseObject(date.toJSONString(), SysRole.class);
            roleService.updateRole(sysRole);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/deleteRole")
    //@RequiresPermissions("user:del")
    public ResponseModel deleteRole(@RequestBody JSONObject date){
        Integer id =date.getInteger("id");
        try{
            roleService.deleteRole(id);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/pageRole")
    public ResponseModel<PageData<PagingRole>> getPageRole(@RequestBody JSONObject date){
        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingRole> data=roleService.pagingRole(pageNo,pageSize,startIndex);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
